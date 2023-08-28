package com.app.elrosal.ui.main

import app.cash.turbine.test
import com.app.data.EnvironmentConfig
import com.app.domain.categories.remote.SubCategories
import com.app.domain.details.ProductDescription
import com.app.domain.user.Whatsapp
import com.app.elrosal.MainViewModel
import com.app.elrosal.sampleDetailProduct
import com.app.elrosal.sampleRecommendedProduct
import com.app.elrosal.sampleSubCategories
import com.app.elrosal.testrules.CoroutinesTestRule
import com.app.elrosal.ui.home.WhatsappUiState
import com.app.elrosal.ui.products.DetailProductUiState
import com.app.elrosal.ui.products.SubCategoriesUiState
import com.app.elrosal.utils.encryptPhoneNumber
import com.app.usecases.categories.GetCategoriesUseCase
import com.app.usecases.categories.GetSubCategoriesUseCase
import com.app.usecases.details.GetDetailProductUseCase
import com.app.usecases.products.GetProductsUseCase
import com.app.usecases.user.GetUserUseCase
import com.app.usecases.user.PostMessageWhatsappUseCase
import com.app.usecases.user.PostUserUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Mock
    lateinit var getSubCategoriesUseCase: GetSubCategoriesUseCase

    @Mock
    lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    lateinit var getDetailProductUseCase: GetDetailProductUseCase

    @Mock
    lateinit var getUserUseCase: GetUserUseCase

    @Mock
    lateinit var postUserUseCase: PostUserUseCase

    @Mock
    lateinit var postMessageWhatsappUseCase: PostMessageWhatsappUseCase


    private lateinit var mainViewModel: MainViewModel


    @Before
    fun setUp() {
        mainViewModel = MainViewModel(
            getCategoriesUseCase = getCategoriesUseCase,
            getSubCategoriesUseCase = getSubCategoriesUseCase,
            getProductsUseCase = getProductsUseCase,
            getDetailProductUseCase = getDetailProductUseCase,
            getUserUseCase = getUserUseCase,
            postUserUseCase = postUserUseCase,
            postMessageWhatsappUseCase = postMessageWhatsappUseCase
        )
    }

    @Test
    fun `State phoneNumber is update with current cache content immediately`() = runTest {
        // Given
        whenever(getUserUseCase()).thenReturn(flowOf("573429544253"))
        mainViewModel.getPhoneNumber()

        // When
        val result = mutableListOf<String?>()
        val job = launch { mainViewModel.phoneNumber.toList(result) }
        runCurrent()
        job.cancel()

        // Then
        assertEquals("573429544253", result[0])
    }

    @Test
    fun `Save phoneNumber cache content`() = runTest {

        mainViewModel.postPhoneNumber(sampleWhatsapp.copy(phoneNumber = "3429544253"))
        runCurrent()

        verify(postUserUseCase).invoke(sampleWhatsapp.phoneNumber.encryptPhoneNumber())

    }

    @Test
    fun `State get detail product is update with request service`() = runTest {
        // Given
        mockGetDetailProductUseCase(
            productDescription = ProductDescription(
                detailProduct = sampleDetailProduct,
                recommendedProducts = sampleRecommendedProduct
            )
        )

        // When
        mainViewModel.getDetailProduct(id = "PSG1vz4CLYLpEkY8XNpJ")

        // Then
        mainViewModel.uiStateDetailProduct.test {
            assertEquals(DetailProductUiState.Loading, awaitItem())
            assertEquals(
                DetailProductUiState.Success(
                    productDescription = ProductDescription(
                        detailProduct = sampleDetailProduct,
                        recommendedProducts = sampleRecommendedProduct
                    )
                ),
                awaitItem()
            )
            cancel()
        }
    }

    @Test
    fun `State get null detail product is update with request service`() = runTest {
        // Given
        mockGetDetailProductUseCase(null)

        // When
        mainViewModel.getDetailProduct(id = "PSG1vz4CLYLpEkY8XNpJ")

        // Then
        mainViewModel.uiStateDetailProduct.test {
            assertEquals(DetailProductUiState.Loading, awaitItem())
            assertEquals(DetailProductUiState.Error(message = "Error"), awaitItem())
            cancel()
        }
    }

    @Test
    fun `State get subCategories is update with request service`() = runTest {
        // Given
        mockGetSubCategoriesUseCase(sampleSubCategories)

        // When
        mainViewModel.getSubCategories(id = "PSG1vz4CLYLpEkY8XNpJ")

        // Then
        mainViewModel.uiStateSubCategories.test {
            assertEquals(SubCategoriesUiState.Loading, awaitItem())
            assertEquals(
                SubCategoriesUiState.Success(subCategories = sampleSubCategories),
                awaitItem()
            )
            cancel()
        }
    }


    @Test
    fun `State get null subCategories  is update with request service`() = runTest {
        // Given
        mockGetSubCategoriesUseCase(null)

        // When
        mainViewModel.getSubCategories(id = "PSG1vz4CLYLpEkY8XNpJ")

        // Then
        mainViewModel.uiStateSubCategories.test {
            assertEquals(SubCategoriesUiState.Loading, awaitItem())
            assertEquals(SubCategoriesUiState.Error(message = "Error"), awaitItem())
            cancel()
        }
    }

    @Test
    fun `PostMessageWhatsapp should update UI state to Success when use case succeeds`() = runTest {

        // Given
        val successCallbackCaptor = argumentCaptor<() -> Unit>()

        whenever(
            postMessageWhatsappUseCase(
                environmentConfig = eq(EnvironmentConfig.PRODUCTION),
                whatsapp = eq(sampleWhatsapp),
                success = successCallbackCaptor.capture(),
                error = any()
            )
        ).thenAnswer {
            successCallbackCaptor.firstValue.invoke()
            WhatsappUiState.Success
        }

        // When
        mainViewModel.postMessageWhatsapp(sampleWhatsapp)
        val result = mutableListOf<WhatsappUiState?>()
        val job = launch { mainViewModel.uiStatePhoneNumber.toList(result) }
        advanceUntilIdle()
        job.cancel()

        // Then
        assertEquals(WhatsappUiState.Success, result[0])
    }


    @Test
    fun `PostMessageWhatsapp should update UI state to Error when use case errors`() = runTest {

        val errorCallbackCaptor = argumentCaptor<(String) -> Unit>()

        whenever(
            postMessageWhatsappUseCase(
                environmentConfig = eq(EnvironmentConfig.PRODUCTION),
                whatsapp = eq(sampleWhatsapp),
                success = any(),
                error = errorCallbackCaptor.capture()
            )
        ).thenAnswer {
            errorCallbackCaptor.firstValue.invoke("Error")
            WhatsappUiState.Error("Error")
        }

        // When
        mainViewModel.postMessageWhatsapp(sampleWhatsapp)
        val result = mutableListOf<WhatsappUiState?>()
        val job = launch { mainViewModel.uiStatePhoneNumber.toList(result) }
        advanceUntilIdle()
        job.cancel()

        // Then
        assertEquals(WhatsappUiState.Error("Error"), result[0])
    }
}


private suspend fun MainViewModelTest.mockGetDetailProductUseCase(productDescription: ProductDescription?) {
    whenever(
        getDetailProductUseCase(
            environmentConfig = EnvironmentConfig.PRODUCTION,
            id = "PSG1vz4CLYLpEkY8XNpJ"
        )
    ).thenReturn(productDescription)
}


private suspend fun MainViewModelTest.mockGetSubCategoriesUseCase(subCategories: SubCategories?) {
    whenever(
        getSubCategoriesUseCase(
            environmentConfig = EnvironmentConfig.PRODUCTION,
            id = "PSG1vz4CLYLpEkY8XNpJ"
        )
    ).thenReturn(subCategories)
}

val sampleWhatsapp = Whatsapp(
    phoneNumber = "573429544253",
    message = "Hola, quiero hacer un pedido",
    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage"
)






