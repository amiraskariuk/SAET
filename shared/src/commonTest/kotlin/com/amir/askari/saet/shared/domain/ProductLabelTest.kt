package com.amir.askari.saet.shared.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProductLabelTest {

    @Test
    fun `maps each known raw label to its text and style`() {
        val expected = mapOf(
            "going-fast" to ProductLabel("Going fast", LabelStyle.Urgent),
            "limited-edition" to ProductLabel("Limited edition", LabelStyle.Urgent),
            "new" to ProductLabel("New", LabelStyle.Highlight),
            "popular" to ProductLabel("Popular", LabelStyle.Highlight),
            "recycled-nylon" to ProductLabel("Recycled nylon", LabelStyle.Sustainable),
            "recycled-polyester" to ProductLabel("Recycled polyester", LabelStyle.Sustainable),
        )

        val actual = expected.keys.associateWith { ProductLabel.fromRaw(it) }

        assertEquals(expected, actual)
    }

    @Test
    fun `maps an unknown label to humanised text with the neutral style`() {
        val raw = "back-in-stock"

        val label = ProductLabel.fromRaw(raw)

        assertEquals(ProductLabel("Back in stock", LabelStyle.Neutral), label)
    }

    @Test
    fun `trims whitespace and ignores case`() {
        val raw = " Going-Fast "

        val label = ProductLabel.fromRaw(raw)

        assertEquals(ProductLabel("Going fast", LabelStyle.Urgent), label)
    }

    @Test
    fun `returns null for a blank label`() {
        val raw = "   "

        val label = ProductLabel.fromRaw(raw)

        assertNull(label)
    }
}
