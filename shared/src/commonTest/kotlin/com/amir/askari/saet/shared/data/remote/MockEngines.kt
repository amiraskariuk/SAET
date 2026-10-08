package com.amir.askari.saet.shared.data.remote

import com.amir.askari.saet.shared.data.ProductFixtures
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

fun jsonEngine(
    body: String = ProductFixtures.PRODUCTS_JSON,
    status: HttpStatusCode = HttpStatusCode.OK,
): MockEngine = MockEngine {
    respond(
        content = body,
        status = status,
        headers = headersOf(HttpHeaders.ContentType, "application/json"),
    )
}
