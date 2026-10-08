package com.amir.askari.saet.shared.domain

interface ProductRepository {
    suspend fun getProducts(forceRefresh: Boolean = false): Result<List<Product>>
    suspend fun getProduct(id: Long): Result<Product>
}
