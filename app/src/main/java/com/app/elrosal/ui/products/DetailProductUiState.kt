package com.app.elrosal.ui.products

import com.app.domain.details.ProductDescription

interface DetailProductUiState {

    object Loading : DetailProductUiState

    data class Error(val message: String) : DetailProductUiState

    data class Success(val productDescription: ProductDescription) : DetailProductUiState
}