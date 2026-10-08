package com.amir.askari.saet.shared.data.remote

import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProductApiTest {

    @Test
    fun `parses hits into dtos`() = runTest {
        val api = ProductApi(createHttpClient(jsonEngine()))

        val dtos = api.fetchProducts()

        assertEquals(listOf(1L, 2L, 3L, null), dtos.map { it.id })
    }

    @Test
    fun `requests the given url`() = runTest {
        val engine = jsonEngine()
        val api = ProductApi(createHttpClient(engine), url = "https://example.com/products.json")

        api.fetchProducts()

        assertEquals("https://example.com/products.json", engine.requestHistory.single().url.toString())
    }

    @Test
    fun `throws on a server error`() = runTest {
        val api = ProductApi(createHttpClient(jsonEngine(body = "", status = HttpStatusCode.InternalServerError)))

        val call = suspend { api.fetchProducts() }

        assertFailsWith<ServerResponseException> { call() }
    }

    @Test
    fun `throws on malformed json`() = runTest {
        val api = ProductApi(createHttpClient(jsonEngine(body = "{ \"hits\": [ { \"id\": ")))

        val call = suspend { api.fetchProducts() }

        assertFailsWith<JsonConvertException> { call() }
    }
}
