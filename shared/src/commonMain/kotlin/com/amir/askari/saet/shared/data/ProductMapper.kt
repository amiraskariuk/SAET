package com.amir.askari.saet.shared.data

import com.amir.askari.saet.shared.data.remote.ProductDto
import com.amir.askari.saet.shared.data.remote.SizeDto
import com.amir.askari.saet.shared.domain.Product
import com.amir.askari.saet.shared.domain.ProductLabel
import com.amir.askari.saet.shared.domain.ProductSize

fun ProductDto.toDomain(): Product? {
    val productId = id ?: return null
    val productTitle = title?.takeIf { it.isNotBlank() } ?: return null
    val sizes = availableSizes.orEmpty().mapNotNull { toProductSize(it) }

    return Product(
        id = productId,
        title = productTitle,
        colour = colour,
        price = price ?: 0,
        labels = labels.orEmpty().mapNotNull { ProductLabel.fromRaw(it) }.distinct(),
        inStock = inStock ?: sizes.any { it.inStock },
        imageUrls = imageUrlsOf(this),
        sizes = sizes,
        descriptionHtml = cleanDescriptionHtml(description.orEmpty()),
        type = type,
        fit = fit,
        sku = sku,
    )
}

fun List<ProductDto>.toDomain(): List<Product> = mapNotNull { it.toDomain() }

private fun imageUrlsOf(dto: ProductDto): List<String> {
    val mediaByPosition = dto.media.orEmpty().sortedBy { it.position ?: Int.MAX_VALUE }
    val candidates = listOf(dto.featuredMedia) + mediaByPosition

    return candidates
        .mapNotNull { it?.src?.trim() }
        .filter { isWebUrl(it) }
        .distinct()
}

private fun isWebUrl(url: String): Boolean =
    url.startsWith("https://") || url.startsWith("http://")

private fun toProductSize(dto: SizeDto): ProductSize? {
    val name = dto.size?.takeIf { it.isNotBlank() } ?: return null
    val quantity = dto.inventoryQuantity
    val inStock = dto.inStock == true && (quantity == null || quantity > 0)
    return ProductSize(name = name.uppercase(), inStock = inStock)
}
