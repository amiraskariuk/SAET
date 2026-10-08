package com.amir.askari.saet.shared.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class PriceFormatterTest {

    @Test
    fun `formats a small price with a pound sign`() {
        val pounds = 65

        val formatted = formatPrice(pounds)

        assertEquals("£65", formatted)
    }

    @Test
    fun `adds a thousands separator`() {
        val pounds = 1000

        val formatted = formatPrice(pounds)

        assertEquals("£1,000", formatted)
    }

    @Test
    fun `adds a separator for every group of three digits`() {
        val pounds = 1234567

        val formatted = formatPrice(pounds)

        assertEquals("£1,234,567", formatted)
    }

    @Test
    fun `formats zero`() {
        val pounds = 0

        val formatted = formatPrice(pounds)

        assertEquals("£0", formatted)
    }
}
