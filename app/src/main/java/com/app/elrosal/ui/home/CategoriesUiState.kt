package com.app.elrosal.ui.home

import com.app.domain.categories.remote.Category


sealed interface CategoriesUiState {
    object Loading : CategoriesUiState
    data class Error(val message: String) : CategoriesUiState
    data class Success(val categories: List<Category>?) : CategoriesUiState
}