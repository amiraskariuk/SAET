package com.amir.askari.saet.ui.list

import androidx.lifecycle.ViewModel
import com.amir.askari.saet.shared.domain.ProductRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    val uiState: StateFlow<ProductListUiState> get() = TODO()

    fun retry(): Unit = TODO()
}
