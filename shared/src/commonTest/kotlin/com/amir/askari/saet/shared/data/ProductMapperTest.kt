package com.amir.askari.saet.shared.data

import com.amir.askari.saet.shared.data.remote.MediaDto
import com.amir.askari.saet.shared.data.remote.ProductDto
import com.amir.askari.saet.shared.data.remote.ProductJson
import com.amir.askari.saet.shared.data.remote.ProductsResponseDto
import com.amir.askari.saet.shared.data.remote.SizeDto
import com.amir.askari.saet.shared.domain.LabelStyle
import com.amir.askari.saet.shared.domain.ProductLabel
import com.amir.askari.saet.shared.domain.ProductSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ProductMapperTest {

    @Test
    fun `maps the basic fields`() {
        val dto = ProductDto(
            id = 42,
            title = "Training Leggings",
            colour = "Navy",
            price = 50,
            type = "Leggings",
            fit = "mid-rise",
            sku = "TL-NAVY",
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals(42L, product.id)
        assertEquals("Training Leggings", product.title)
        assertEquals("Navy", product.colour)
        assertEquals(50, product.price)
        assertEquals("Leggings", product.type)
        assertEquals("mid-rise", product.fit)
        assertEquals("TL-NAVY", product.sku)
    }

    @Test
    fun `uses zero price and empty description when they are missing`() {
        val dto = ProductDto(id = 1, title = "Leggings", price = null, description = null)

        val product = assertNotNull(dto.toDomain())

        assertEquals(0, product.price)
        assertEquals("", product.descriptionHtml)
    }

    @Test
    fun `puts the featured image first then media ordered by position without duplicates`() {
        val dto = ProductDto(
            id = 1,
            title = "Leggings",
            featuredMedia = MediaDto(src = "https://cdn.example.com/a.jpg", position = 1),
            media = listOf(
                MediaDto(src = "https://cdn.example.com/c.jpg", position = 3),
                MediaDto(src = "https://cdn.example.com/a.jpg", position = 1),
                MediaDto(src = "https://cdn.example.com/b.jpg", position = 2),
            ),
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals(
            listOf(
                "https://cdn.example.com/a.jpg",
                "https://cdn.example.com/b.jpg",
                "https://cdn.example.com/c.jpg",
            ),
            product.imageUrls,
        )
    }

    @Test
    fun `falls back to the first valid media image when the featured image is missing`() {
        val dto = ProductDto(
            id = 1,
            title = "Leggings",
            featuredMedia = null,
            media = listOf(
                MediaDto(src = "", position = 1),
                MediaDto(src = "https://cdn.example.com/b.jpg", position = 2),
            ),
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals("https://cdn.example.com/b.jpg", product.imageUrl)
    }

    @Test
    fun `has no image url when there are no valid images`() {
        val dto = ProductDto(id = 1, title = "Leggings", featuredMedia = null, media = null)

        val product = assertNotNull(dto.toDomain())

        assertNull(product.imageUrl)
        assertTrue(product.imageUrls.isEmpty())
    }

    @Test
    fun `ignores blank and non-http image urls`() {
        val dto = ProductDto(
            id = 1,
            title = "Leggings",
            featuredMedia = MediaDto(src = "   ", position = 1),
            media = listOf(
                MediaDto(src = "ftp://cdn.example.com/a.jpg", position = 1),
                MediaDto(src = "//cdn.example.com/b.jpg", position = 2),
                MediaDto(src = null, position = 3),
                MediaDto(src = "http://cdn.example.com/c.jpg", position = 4),
                MediaDto(src = "https://cdn.example.com/d.jpg", position = 5),
            ),
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals(
            listOf("http://cdn.example.com/c.jpg", "https://cdn.example.com/d.jpg"),
            product.imageUrls,
        )
    }

    @Test
    fun `drops products without an id or title`() {
        val dtos = listOf(
            ProductDto(id = 1, title = "Leggings"),
            ProductDto(id = null, title = "No id"),
            ProductDto(id = 2, title = null),
            ProductDto(id = 3, title = "  "),
        )

        val products = dtos.toDomain()

        assertEquals(listOf(1L), products.map { it.id })
    }

    @Test
    fun `maps labels and skips blank ones`() {
        val dto = ProductDto(id = 1, title = "Leggings", labels = listOf("going-fast", " ", "new"))

        val product = assertNotNull(dto.toDomain())

        assertEquals(
            listOf(
                ProductLabel("Going fast", LabelStyle.Urgent),
                ProductLabel("New", LabelStyle.Highlight),
            ),
            product.labels,
        )
    }

    @Test
    fun `removes duplicate labels`() {
        val dto = ProductDto(id = 1, title = "Leggings", labels = listOf("going-fast", "Going-Fast"))

        val product = assertNotNull(dto.toDomain())

        assertEquals(listOf(ProductLabel("Going fast", LabelStyle.Urgent)), product.labels)
    }

    @Test
    fun `turns a null labels list into an empty list`() {
        val dto = ProductDto(id = 1, title = "Leggings", labels = null)

        val product = assertNotNull(dto.toDomain())

        assertTrue(product.labels.isEmpty())
    }

    @Test
    fun `marks a size as sold out when its flag is false or its inventory is zero or negative`() {
        val dto = ProductDto(
            id = 1,
            title = "Leggings",
            availableSizes = listOf(
                SizeDto(size = "xs", inStock = true, inventoryQuantity = 5),
                SizeDto(size = "s", inStock = false, inventoryQuantity = 5),
                SizeDto(size = "m", inStock = true, inventoryQuantity = 0),
                SizeDto(size = "l", inStock = true, inventoryQuantity = -6),
                SizeDto(size = "xl", inStock = true, inventoryQuantity = null),
            ),
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals(
            listOf(
                ProductSize("XS", inStock = true),
                ProductSize("S", inStock = false),
                ProductSize("M", inStock = false),
                ProductSize("L", inStock = false),
                ProductSize("XL", inStock = true),
            ),
            product.sizes,
        )
    }

    @Test
    fun `drops sizes without a name`() {
        val dto = ProductDto(
            id = 1,
            title = "Leggings",
            availableSizes = listOf(
                SizeDto(size = null, inStock = true),
                SizeDto(size = " ", inStock = true),
                SizeDto(size = "m", inStock = true),
            ),
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals(listOf("M"), product.sizes.map { it.name })
    }

    @Test
    fun `uses size availability when the product stock flag is missing`() {
        val dto = ProductDto(
            id = 1,
            title = "Leggings",
            inStock = null,
            availableSizes = listOf(SizeDto(size = "xs", inStock = false, inventoryQuantity = 0)),
        )

        val product = assertNotNull(dto.toDomain())

        assertEquals(false, product.inStock)
    }

    @Test
    fun `parses the fixture json ignoring unknown keys and returns three products`() {
        val json = ProductFixtures.PRODUCTS_JSON

        val products = ProductJson.decodeFromString<ProductsResponseDto>(json).hits.toDomain()

        assertEquals(listOf(1L, 2L, 3L), products.map { it.id })
    }
}
