package com.app.elrosal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.data.EnvironmentConfig
import com.app.domain.products.Product
import com.app.domain.user.Whatsapp
import com.app.elrosal.ui.home.CategoriesUiState
import com.app.elrosal.ui.home.WhatsappUiState
import com.app.elrosal.ui.products.DetailProductUiState
import com.app.elrosal.ui.home.CategoriesUiState.Success as CategoriesSuccess
import com.app.elrosal.ui.products.SubCategoriesUiState
import com.app.elrosal.ui.products.paginate.ProductsPagingSource
import com.app.elrosal.utils.encryptPhoneNumber
import com.app.usecases.categories.GetCategoriesUseCase
import com.app.usecases.categories.GetSubCategoriesUseCase
import com.app.usecases.details.GetDetailProductUseCase
import com.app.usecases.products.GetProductsUseCase
import com.app.usecases.user.GetUserUseCase
import com.app.usecases.user.PostMessageWhatsappUseCase
import com.app.usecases.user.PostUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getDetailProductUseCase: GetDetailProductUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val postUserUseCase: PostUserUseCase,
    private val postMessageWhatsappUseCase: PostMessageWhatsappUseCase
) : ViewModel() {


    val uiStateCategories: StateFlow<CategoriesUiState> =
        getCategoriesUseCase(environmentConfig = EnvironmentConfig.PRODUCTION).map(::CategoriesSuccess)
            .catch { error -> CategoriesUiState.Error(error.message ?: "Error") }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CategoriesUiState.Loading
            )

    private val _uiStateSubCategories =
        MutableStateFlow<SubCategoriesUiState>(SubCategoriesUiState.Loading)
    val uiStateSubCategories: StateFlow<SubCategoriesUiState> = _uiStateSubCategories.asStateFlow()

    private val _uiStateDetailProduct =
        MutableStateFlow<DetailProductUiState>(DetailProductUiState.Loading)
    val uiStateDetailProduct: StateFlow<DetailProductUiState> = _uiStateDetailProduct.asStateFlow()

    private val subCategoryIdFlow = MutableStateFlow("")

    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber: StateFlow<String?> = _phoneNumber.asStateFlow()

    private val _uiStatePhoneNumber = MutableStateFlow<WhatsappUiState?>(null)
    val uiStatePhoneNumber: StateFlow<WhatsappUiState?> = _uiStatePhoneNumber.asStateFlow()

    fun getPhoneNumber() {
        viewModelScope.launch {
            getUserUseCase().collect { phoneNumber ->
                _phoneNumber.update { phoneNumber }
            }
        }
    }

    fun getSubCategories(id: String) {
        viewModelScope.launch {
            getSubCategoriesUseCase(
                environmentConfig = EnvironmentConfig.PRODUCTION,
                id = id,
                subCategories = { subCategories ->
                    _uiStateSubCategories.update { SubCategoriesUiState.Success(subCategories) }
                },
                error = { error ->
                    _uiStateSubCategories.update { SubCategoriesUiState.Error(error) }
                })
        }
    }

    fun setSubCategoryId(id: String) {
        subCategoryIdFlow.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val products: Flow<PagingData<Product>> = subCategoryIdFlow.flatMapLatest { id ->
        Pager(
            pagingSourceFactory = {
                ProductsPagingSource(
                    id = id,
                    getProductsUseCase = getProductsUseCase
                )
            },
            config = PagingConfig(pageSize = 10)
        ).flow
    }.cachedIn(viewModelScope)

    fun getDetailProduct(id: String) {
        viewModelScope.launch {
            getDetailProductUseCase(
                environmentConfig = EnvironmentConfig.PRODUCTION,
                id = id,
                detailProduct = { detailProduct ->
                    _uiStateDetailProduct.update { DetailProductUiState.Success(detailProduct) }
                },
                error = { error ->
                    _uiStateDetailProduct.update { DetailProductUiState.Error(error) }
                }
            )
        }
    }

    fun postPhoneNumber(whatsapp: Whatsapp) {
        viewModelScope.launch {
            val phoneNumber = "57${whatsapp.phoneNumber}"
            postUserUseCase(phoneNumber = phoneNumber.encryptPhoneNumber())
            postMessageWhatsapp(whatsapp.copy(phoneNumber = phoneNumber))
        }
    }

    fun postMessageWhatsapp(whatsapp: Whatsapp) {
        _uiStatePhoneNumber.update { WhatsappUiState.Loading }
        viewModelScope.launch {
            postMessageWhatsappUseCase(
                environmentConfig = EnvironmentConfig.PRODUCTION,
                whatsapp = whatsapp,
                success = {
                    _uiStatePhoneNumber.update { WhatsappUiState.Success }
                },
                error = { error ->
                    _uiStatePhoneNumber.update { WhatsappUiState.Error(error) }
                }
            )
        }
    }
}