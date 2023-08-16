package com.app.elrosal.ui.home

import com.app.domain.categories.remote.Category

interface WhatsappUiState {
    object Loading : WhatsappUiState
    data class Error(val message: String) : WhatsappUiState
    object Success : WhatsappUiState
}