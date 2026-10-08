package com.amir.askari.saet.shared.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseDto(
    val hits: List<ProductDto> = emptyList(),
)

@Serializable
data class ProductDto(
    val id: Long? = null,
    val title: String? = null,
    val colour: String? = null,
    val price: Int? = null,
    val labels: List<String>? = null,
    val inStock: Boolean? = null,
    val featuredMedia: MediaDto? = null,
    val media: List<MediaDto>? = null,
    val availableSizes: List<SizeDto>? = null,
    val description: String? = null,
    val type: String? = null,
    val fit: String? = null,
    val sku: String? = null,
)

@Serializable
data class MediaDto(
    val src: String? = null,
    val position: Int? = null,
)

@Serializable
data class SizeDto(
    val size: String? = null,
    val inStock: Boolean? = null,
    val inventoryQuantity: Int? = null,
)
