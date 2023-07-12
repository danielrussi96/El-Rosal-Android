package com.app.elrosal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.EnvironmentConfig
import com.app.domain.categories.SubCategories
import com.app.domain.products.Products
import com.app.elrosal.ui.home.CategoriesUiState
import com.app.usecases.categories.GetCategoriesUseCase
import com.app.usecases.categories.GetSubCategoriesUseCase
import com.app.usecases.products.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiStateCategories = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiStateCategories: StateFlow<CategoriesUiState> = _uiStateCategories.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoriesUseCase(environmentConfig = EnvironmentConfig.PRODUCTION,
                listCategories = { list ->
                    _uiStateCategories.update { CategoriesUiState.Success(list) }
                },
                error = { error ->
                    _uiStateCategories.update { CategoriesUiState.Error(error) }
                })
        }
    }

    fun getSubCategories(id: String, callback: (SubCategories) -> Unit) {
        viewModelScope.launch {
            val categories = getSubCategoriesUseCase(EnvironmentConfig.PRODUCTION, id)
            callback(categories)
        }
    }

    fun productsCategories(id: String, callback: (Products) -> Unit) {
        viewModelScope.launch {
            val categories = getProductsUseCase(EnvironmentConfig.PRODUCTION, id, 1)
            callback(categories)
        }
    }
}