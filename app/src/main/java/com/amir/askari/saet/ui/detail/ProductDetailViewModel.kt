package com.amir.askari.saet.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.askari.saet.shared.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository,
) : ViewModel() {

    private val productId: Long = checkNotNull(savedStateHandle.get<Long>("productId"))

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun retry() {
        load()
    }

    private fun load() {
        _uiState.value = ProductDetailUiState.Loading
        viewModelScope.launch {
            _uiState.value = repository.getProduct(productId).fold(
                onSuccess = { product -> ProductDetailUiState.Content(product) },
                onFailure = { ProductDetailUiState.Error },
            )
        }
    }
}
