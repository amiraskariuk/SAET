package com.amir.askari.saet.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.amir.askari.saet.testing.FakeProductRepository
import com.amir.askari.saet.testing.MainDispatcherRule
import com.amir.askari.saet.testing.testProduct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val products = listOf(testProduct(id = 1), testProduct(id = 2, title = "Flex Leggings"))

    @Test
    fun `shows content for the id in the saved state handle`() = runTest {
        val repository = FakeProductRepository(Result.success(products))

        val viewModel = ProductDetailViewModel(savedStateFor(productId = 2), repository)
        advanceUntilIdle()

        assertEquals(ProductDetailUiState.Content(products[1]), viewModel.uiState.value)
    }

    @Test
    fun `shows error when the product is not found`() = runTest {
        val repository = FakeProductRepository(Result.success(products))

        val viewModel = ProductDetailViewModel(savedStateFor(productId = 99), repository)
        advanceUntilIdle()

        assertEquals(ProductDetailUiState.Error, viewModel.uiState.value)
    }

    @Test
    fun `retry after an error shows content`() = runTest {
        val repository = FakeProductRepository(Result.failure(Exception("offline")))
        val viewModel = ProductDetailViewModel(savedStateFor(productId = 2), repository)
        advanceUntilIdle()
        repository.productsResult = Result.success(products)

        viewModel.retry()
        advanceUntilIdle()

        assertEquals(ProductDetailUiState.Content(products[1]), viewModel.uiState.value)
    }

    private fun savedStateFor(productId: Long) = SavedStateHandle(mapOf("productId" to productId))
}
