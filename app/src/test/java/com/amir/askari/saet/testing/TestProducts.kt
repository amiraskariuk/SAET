package com.amir.askari.saet.testing

import com.amir.askari.saet.shared.domain.Product
import com.amir.askari.saet.shared.domain.ProductLabel
import com.amir.askari.saet.shared.domain.ProductSize

fun testProduct(
    id: Long = 1,
    title: String = "Training Leggings",
    colour: String? = "Navy",
    price: Int = 50,
    labels: List<ProductLabel> = emptyList(),
    inStock: Boolean = true,
    imageUrls: List<String> = emptyList(),
    sizes: List<ProductSize> = emptyList(),
    descriptionHtml: String = "",
): Product = Product(
    id = id,
    title = title,
    colour = colour,
    price = price,
    labels = labels,
    inStock = inStock,
    imageUrls = imageUrls,
    sizes = sizes,
    descriptionHtml = descriptionHtml,
    type = null,
    fit = null,
    sku = null,
)
