package com.amir.askari.saet.shared.data.remote

import io.ktor.client.HttpClient

const val PRODUCTS_URL =
    "https://cdn.develop.gymshark.com/training/mock-product-responses/algolia-example-payload.json"

class ProductApi(
    private val client: HttpClient,
    private val url: String = PRODUCTS_URL,
) {
    suspend fun fetchProducts(): List<ProductDto> = TODO()
}
