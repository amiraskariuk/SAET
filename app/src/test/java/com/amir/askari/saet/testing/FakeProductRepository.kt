package com.amir.askari.saet.testing

import com.amir.askari.saet.shared.domain.Product
import com.amir.askari.saet.shared.domain.ProductNotFoundException
import com.amir.askari.saet.shared.domain.ProductRepository

class FakeProductRepository(
    var productsResult: Result<List<Product>> = Result.success(emptyList()),
) : ProductRepository {

    val forceRefreshRequests = mutableListOf<Boolean>()

    val getProductsCalls: Int get() = forceRefreshRequests.size

    override suspend fun getProducts(forceRefresh: Boolean): Result<List<Product>> {
        forceRefreshRequests += forceRefresh
        return productsResult
    }

    override suspend fun getProduct(id: Long): Result<Product> =
        productsResult.mapCatching { products ->
            products.find { it.id == id } ?: throw ProductNotFoundException(id)
        }
}
