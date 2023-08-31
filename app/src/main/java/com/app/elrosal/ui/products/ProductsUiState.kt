package com.app.elrosal.ui.products

import androidx.paging.PagingData
import com.app.domain.products.Product

interface ProductsUiState {
    object Loading : ProductsUiState

    data class Error(val message: String) : ProductsUiState

    data class Success(val products: PagingData<Product>) : ProductsUiState
}