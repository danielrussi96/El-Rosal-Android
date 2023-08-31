package com.app.elrosal.ui.main

import com.app.elrosal.MainViewModel
import com.app.elrosal.testrules.CoroutinesTestRule
import com.app.elrosal.ui.home.CategoriesUiState
import com.app.elrosal.ui.home.WhatsappUiState
import com.app.elrosal.ui.products.DetailProductUiState
import com.app.elrosal.ui.products.SubCategoriesUiState
import com.app.testsharedapp.initMockedDi
import com.app.testsharedapp.sampleWhatsapp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.get
import org.koin.test.AutoCloseKoinTest
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var mainViewModel: MainViewModel


    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get(), get(), get(), get(), get(), get()) }
        }
        initMockedDi(vmModule)
        mainViewModel = get()
        mainViewModel.getPhoneNumber()
    }

    @Test
    fun `Data is loaded from server get subCategories`() = runTest {
        mainViewModel.getSubCategories("85PXtMUmI3pIHUQ33P1D")

        val uiStates = mainViewModel.uiStateSubCategories.take(2).toList()

        assertEquals(SubCategoriesUiState.Loading, uiStates[0])

        val subCategoriesUiState = uiStates[1]
        assertTrue(subCategoriesUiState is SubCategoriesUiState.Success)

        val subCategories = (subCategoriesUiState as SubCategoriesUiState.Success).subCategories
        assertEquals("Desayunos", subCategories.name)

        assertEquals("Saludable", subCategories.subCategories[0].name)

    }

    @Test
    fun `Data is loaded from server get detailProduct`() = runTest {
        mainViewModel.getDetailProduct("56SZxrcDUbklUztkPinj")

        val uiStates = mainViewModel.uiStateDetailProduct.take(2).toList()

        assertEquals(DetailProductUiState.Loading, uiStates[0])

        val detailProductUiState = uiStates[1]
        assertTrue(detailProductUiState is DetailProductUiState.Success)

        val detailProduct = (detailProductUiState as DetailProductUiState.Success).productDescription
        assertEquals("SI003", detailProduct.detailProduct.name)
        assertEquals("La Sirenita", detailProduct.detailProduct.description)
    }

    @Test
    fun `Data is loaded from server get categories`() = runTest {
        val uiStates = mainViewModel.uiStateCategories.take(2).toList()

        assertEquals(CategoriesUiState.Loading, uiStates[0])

        val categoriesUiState = uiStates[1]
        assertTrue(categoriesUiState is CategoriesUiState.Success)

        val categories = (categoriesUiState as CategoriesUiState.Success).categories

        assertEquals("Desayunos", categories!![0].name)
        assertEquals(4, categories.size)
    }


    @Test
    fun `Data is loaded from server whatsapp`() = runTest {
        mainViewModel.postMessageWhatsapp(sampleWhatsapp)

        val uiStates = mainViewModel.uiStatePhoneNumber.take(2).toList()

        assertEquals(WhatsappUiState.Loading, uiStates[0])

        val whatsappUiStateUiState = uiStates[1]
        assertTrue(whatsappUiStateUiState is WhatsappUiState.Success)
    }

    @Test
    fun `Data is loaded from local database phoneNumber`() = runTest {
        val phoneNumber = mainViewModel.phoneNumber.take(1).toList()

        assertEquals("573429544253", phoneNumber[0])

    }
}





