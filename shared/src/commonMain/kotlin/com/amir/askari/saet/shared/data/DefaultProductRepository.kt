package com.amir.askari.saet.shared.data

import com.amir.askari.saet.shared.data.remote.ProductApi
import com.amir.askari.saet.shared.domain.Product
import com.amir.askari.saet.shared.domain.ProductNotFoundException
import com.amir.askari.saet.shared.domain.ProductRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.cancellation.CancellationException

class DefaultProductRepository(private val api: ProductApi) : ProductRepository {

    private val mutex = Mutex()
    private var cachedProducts: List<Product> = emptyList()

    override suspend fun getProducts(forceRefresh: Boolean): Result<List<Product>> =
        catchFailures { loadProducts(forceRefresh) }

    override suspend fun getProduct(id: Long): Result<Product> =
        catchFailures {
            loadProducts(forceRefresh = false).find { it.id == id } ?: throw ProductNotFoundException(id)
        }

    private suspend fun loadProducts(forceRefresh: Boolean): List<Product> =
        mutex.withLock {
            if (forceRefresh || cachedProducts.isEmpty()) {
                cachedProducts = api.fetchProducts().toDomain()
            }
            cachedProducts
        }

    private inline fun <T> catchFailures(block: () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
