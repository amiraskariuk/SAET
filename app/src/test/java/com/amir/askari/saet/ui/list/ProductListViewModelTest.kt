package com.amir.askari.saet.ui.list

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
class ProductListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val products = listOf(testProduct(id = 1), testProduct(id = 2, title = "Flex Leggings"))

    @Test
    fun `is loading before the repository returns`() = runTest {
        val repository = FakeProductRepository(Result.success(products))

        val viewModel = ProductListViewModel(repository)

        assertEquals(ProductListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `shows content with the products on success`() = runTest {
        val repository = FakeProductRepository(Result.success(products))

        val viewModel = ProductListViewModel(repository)
        advanceUntilIdle()

        assertEquals(ProductListUiState.Content(products), viewModel.uiState.value)
    }

    @Test
    fun `shows error when the repository fails`() = runTest {
        val repository = FakeProductRepository(Result.failure(Exception("offline")))

        val viewModel = ProductListViewModel(repository)
        advanceUntilIdle()

        assertEquals(ProductListUiState.Error, viewModel.uiState.value)
    }

    @Test
    fun `retry after an error loads again with force refresh and shows content`() = runTest {
        val repository = FakeProductRepository(Result.failure(Exception("offline")))
        val viewModel = ProductListViewModel(repository)
        advanceUntilIdle()
        repository.productsResult = Result.success(products)

        viewModel.retry()
        advanceUntilIdle()

        assertEquals(ProductListUiState.Content(products), viewModel.uiState.value)
        assertEquals(listOf(false, true), repository.forceRefreshRequests)
    }
}
