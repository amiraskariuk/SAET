package com.amir.askari.saet.shared.domain

fun formatPrice(pounds: Int): String {
    val grouped = pounds.toString().reversed().chunked(3).joinToString(",").reversed()
    return "£$grouped"
}
