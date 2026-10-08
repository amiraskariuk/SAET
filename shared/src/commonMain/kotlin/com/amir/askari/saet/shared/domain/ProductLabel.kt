package com.amir.askari.saet.shared.domain

data class ProductLabel(val text: String, val style: LabelStyle) {

    companion object {
        fun fromRaw(raw: String): ProductLabel? = TODO()
    }
}
