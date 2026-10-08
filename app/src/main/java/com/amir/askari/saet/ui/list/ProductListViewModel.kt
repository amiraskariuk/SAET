package com.amir.askari.saet.ui.list

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
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        load(forceRefresh = false)
    }

    fun retry() {
        load(forceRefresh = true)
    }

    private fun load(forceRefresh: Boolean) {
        _uiState.value = ProductListUiState.Loading
        viewModelScope.launch {
            _uiState.value = repository.getProducts(forceRefresh).fold(
                onSuccess = { products -> ProductListUiState.Content(products) },
                onFailure = { ProductListUiState.Error },
            )
        }
    }
}
