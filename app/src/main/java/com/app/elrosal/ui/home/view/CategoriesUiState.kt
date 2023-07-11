package com.app.elrosal.ui.home.view

import com.app.domain.categories.CategoriesItem


sealed interface CategoriesUiState {
    object Loading : CategoriesUiState
    data class Error(val message: String) : CategoriesUiState
    data class Success(val categories: List<CategoriesItem>) : CategoriesUiState
}