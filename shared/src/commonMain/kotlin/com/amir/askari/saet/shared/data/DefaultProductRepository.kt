package com.amir.askari.saet.shared.data

import com.amir.askari.saet.shared.data.remote.ProductApi
import com.amir.askari.saet.shared.domain.Product
import com.amir.askari.saet.shared.domain.ProductRepository

class DefaultProductRepository(private val api: ProductApi) : ProductRepository {

    override suspend fun getProducts(forceRefresh: Boolean): Result<List<Product>> = TODO()

    override suspend fun getProduct(id: Long): Result<Product> = TODO()
}
