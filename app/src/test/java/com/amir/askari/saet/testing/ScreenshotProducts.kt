package com.amir.askari.saet.testing

import com.amir.askari.saet.shared.domain.LabelStyle
import com.amir.askari.saet.shared.domain.ProductLabel
import com.amir.askari.saet.shared.domain.ProductSize

val screenshotProducts = listOf(
    testProduct(
        id = 1,
        title = "Speed Leggings",
        colour = "Navy",
        price = 1000,
        labels = listOf(ProductLabel("Going fast", LabelStyle.Urgent)),
    ),
    testProduct(
        id = 2,
        title = "Adapt Ombre Seamless Leggings",
        colour = "Rose Pink/Light Blue",
        price = 65,
        labels = listOf(
            ProductLabel("New", LabelStyle.Highlight),
            ProductLabel("Recycled nylon", LabelStyle.Sustainable),
        ),
    ),
    testProduct(id = 3, title = "KK Fit 7/8 Leggings", colour = "Earth Orange", price = 60, inStock = false),
    testProduct(id = 4, title = "Training Leggings", colour = "Black", price = 50),
)

val screenshotDetailProduct = testProduct(
    id = 1,
    title = "Speed Leggings",
    colour = "Navy",
    price = 1000,
    labels = listOf(ProductLabel("Going fast", LabelStyle.Urgent)),
    sizes = listOf(
        ProductSize("XS", inStock = true),
        ProductSize("S", inStock = false),
        ProductSize("M", inStock = false),
        ProductSize("L", inStock = true),
        ProductSize("XL", inStock = true),
    ),
    descriptionHtml = "<p><strong>RUN WITH IT</strong></p>" +
        "<p>Lightweight, ventilating fabrics that move with you.</p>" +
        "<p>- Full length legging<br>- High-waisted<br>- Compressive fit</p>",
)
