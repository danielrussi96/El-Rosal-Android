package com.app.elrosal.ui.home


interface WhatsappUiState {
    object Loading : WhatsappUiState
    data class Error(val message: String) : WhatsappUiState
    object Success : WhatsappUiState
}