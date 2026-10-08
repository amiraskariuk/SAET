package com.amir.askari.saet.shared.domain

data class ProductLabel(val text: String, val style: LabelStyle) {

    companion object {
        fun fromRaw(raw: String): ProductLabel? {
            val key = raw.trim().lowercase()
            if (key.isEmpty()) return null
            return knownLabel(key) ?: ProductLabel(humanise(key), LabelStyle.Neutral)
        }

        private fun knownLabel(key: String): ProductLabel? = when (key) {
            "going-fast" -> ProductLabel("Going fast", LabelStyle.Urgent)
            "limited-edition" -> ProductLabel("Limited edition", LabelStyle.Urgent)
            "new" -> ProductLabel("New", LabelStyle.Highlight)
            "popular" -> ProductLabel("Popular", LabelStyle.Highlight)
            "recycled-nylon" -> ProductLabel("Recycled nylon", LabelStyle.Sustainable)
            "recycled-polyester" -> ProductLabel("Recycled polyester", LabelStyle.Sustainable)
            else -> null
        }

        private fun humanise(key: String): String =
            key.replace('-', ' ').replaceFirstChar { it.uppercase() }
    }
}
