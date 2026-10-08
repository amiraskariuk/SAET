package com.amir.askari.saet.shared.domain

data class Product(
    val id: Long,
    val title: String,
    val colour: String?,
    val price: Int,
    val labels: List<ProductLabel>,
    val inStock: Boolean,
    val imageUrls: List<String>,
    val sizes: List<ProductSize>,
    val descriptionHtml: String,
    val type: String?,
    val fit: String?,
    val sku: String?,
) {
    val imageUrl: String? get() = imageUrls.firstOrNull()
}
