package com.amir.askari.saet.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object ProductListRoute

@Serializable
data class ProductDetailRoute(val productId: Long)
