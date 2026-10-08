package com.amir.askari.saet.ui.list

import com.amir.askari.saet.shared.domain.Product

sealed interface ProductListUiState {
    data object Loading : ProductListUiState
    data class Content(val products: List<Product>) : ProductListUiState
    data object Error : ProductListUiState
}
