package com.amir.askari.saet.shared.data

import com.amir.askari.saet.shared.data.remote.ProductApi
import com.amir.askari.saet.shared.data.remote.createHttpClient
import com.amir.askari.saet.shared.data.remote.jsonEngine
import com.amir.askari.saet.shared.domain.ProductNotFoundException
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class DefaultProductRepositoryTest {

    @Test
    fun `returns mapped products`() = runTest {
        val repository = repository(jsonEngine())

        val result = repository.getProducts()

        assertEquals(listOf(1L, 2L, 3L), result.getOrThrow().map { it.id })
    }

    @Test
    fun `serves the second call from the cache`() = runTest {
        val engine = jsonEngine()
        val repository = repository(engine)

        repository.getProducts()
        repository.getProducts()

        assertEquals(1, engine.requestHistory.size)
    }

    @Test
    fun `fetches again when force refresh is true`() = runTest {
        val engine = jsonEngine()
        val repository = repository(engine)

        repository.getProducts()
        repository.getProducts(forceRefresh = true)

        assertEquals(2, engine.requestHistory.size)
    }

    @Test
    fun `returns a failure when the api errors`() = runTest {
        val repository = repository(jsonEngine(body = "", status = HttpStatusCode.InternalServerError))

        val result = repository.getProducts()

        assertIs<ServerResponseException>(result.exceptionOrNull())
    }

    @Test
    fun `rethrows cancellation instead of returning a failure`() = runTest {
        val repository = repository(MockEngine { throw CancellationException("cancelled") })

        val call = suspend { repository.getProducts() }

        assertFailsWith<CancellationException> { call() }
    }

    @Test
    fun `getProduct returns a cached product by id`() = runTest {
        val engine = jsonEngine()
        val repository = repository(engine)
        repository.getProducts()

        val result = repository.getProduct(2)

        assertEquals("Flex High Waisted Leggings", result.getOrThrow().title)
        assertEquals(1, engine.requestHistory.size)
    }

    @Test
    fun `getProduct fetches when the cache is empty`() = runTest {
        val engine = jsonEngine()
        val repository = repository(engine)

        val result = repository.getProduct(2)

        assertEquals(2L, result.getOrThrow().id)
        assertEquals(1, engine.requestHistory.size)
    }

    @Test
    fun `getProduct fails with ProductNotFoundException for an unknown id`() = runTest {
        val repository = repository(jsonEngine())

        val result = repository.getProduct(99)

        assertIs<ProductNotFoundException>(result.exceptionOrNull())
    }

    private fun repository(engine: HttpClientEngine) =
        DefaultProductRepository(ProductApi(createHttpClient(engine)))
}
