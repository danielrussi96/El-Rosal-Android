package com.app.elrosal.ui.products

import com.app.domain.categories.remote.SubCategories


sealed interface SubCategoriesUiState {
    object Loading : SubCategoriesUiState
    data class Error(val message: String) : SubCategoriesUiState
    data class Success(val subCategories: SubCategories) : SubCategoriesUiState
}