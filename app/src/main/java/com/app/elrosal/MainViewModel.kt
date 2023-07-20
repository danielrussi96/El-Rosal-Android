package com.app.elrosal

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.data.EnvironmentConfig
import com.app.domain.products.Product
import com.app.elrosal.ui.home.CategoriesUiState
import com.app.elrosal.ui.products.DetailProductUiState
import com.app.elrosal.ui.products.ProductsUiState
import com.app.elrosal.ui.products.ProductsUiState.Success
import com.app.elrosal.ui.products.ProductsUiState.Error
import com.app.elrosal.ui.products.SubCategoriesUiState
import com.app.elrosal.ui.products.paginate.ProductsPagingSource
import com.app.usecases.categories.GetCategoriesUseCase
import com.app.usecases.categories.GetSubCategoriesUseCase
import com.app.usecases.details.GetDetailProductUseCase
import com.app.usecases.products.GetProductsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getDetailProductUseCase: GetDetailProductUseCase
) : ViewModel() {

    private val _uiStateCategories = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiStateCategories: StateFlow<CategoriesUiState> = _uiStateCategories.asStateFlow()

    private val _uiStateSubCategories =
        MutableStateFlow<SubCategoriesUiState>(SubCategoriesUiState.Loading)
    val uiStateSubCategories: StateFlow<SubCategoriesUiState> = _uiStateSubCategories.asStateFlow()

    private val _uiStateDetailProduct =
        MutableStateFlow<DetailProductUiState>(DetailProductUiState.Loading)
    val uiStateDetailProduct: StateFlow<DetailProductUiState> = _uiStateDetailProduct.asStateFlow()

    private val subCategoryIdFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            getCategoriesUseCase(environmentConfig = EnvironmentConfig.PRODUCTION,
                categories = { list ->
                    _uiStateCategories.update { CategoriesUiState.Success(list) }
                },
                error = { error ->
                    _uiStateCategories.update { CategoriesUiState.Error(error) }
                })

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
}