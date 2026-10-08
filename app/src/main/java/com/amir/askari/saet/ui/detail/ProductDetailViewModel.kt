package com.amir.askari.saet.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.amir.askari.saet.shared.domain.ProductRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository,
) : ViewModel() {

    val uiState: StateFlow<ProductDetailUiState> get() = TODO()

    fun retry(): Unit = TODO()
}
