package com.amir.askari.saet.ui.detail

import com.amir.askari.saet.shared.domain.Product

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Content(val product: Product) : ProductDetailUiState
    data object Error : ProductDetailUiState
}
