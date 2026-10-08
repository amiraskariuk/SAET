package com.amir.askari.saet.shared.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

const val PRODUCTS_URL =
    "https://cdn.develop.gymshark.com/training/mock-product-responses/algolia-example-payload.json"

class ProductApi(
    private val client: HttpClient,
    private val url: String = PRODUCTS_URL,
) {
    suspend fun fetchProducts(): List<ProductDto> =
        client.get(url).body<ProductsResponseDto>().hits
}
