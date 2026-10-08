package com.amir.askari.saet.shared.data

import kotlin.test.Test
import kotlin.test.assertEquals

class DescriptionHtmlCleanerTest {

    @Test
    fun `removes meta tags`() {
        val html = "<meta charset=\"utf-8\"><p>Soft and <meta charset=\"utf-8\">stretchy</p>"

        val cleaned = cleanDescriptionHtml(html)

        assertEquals("<p>Soft and stretchy</p>", cleaned)
    }

    @Test
    fun `removes line breaks at the start of a paragraph`() {
        val html = "<p><br data-mce-fragment=\"1\">Soft and stretchy</p>"

        val cleaned = cleanDescriptionHtml(html)

        assertEquals("<p>Soft and stretchy</p>", cleaned)
    }

    @Test
    fun `removes empty paragraphs`() {
        val html = "<p>Soft</p>\n<p> <br></p>\n<p>&nbsp;</p>\n<p>\u00A0</p>\n<p>Stretchy</p>"

        val cleaned = cleanDescriptionHtml(html)

        assertEquals("<p>Soft</p>\n<p>Stretchy</p>", cleaned)
    }

    @Test
    fun `keeps strong text and the line breaks between bullet lines`() {
        val html = "<p><strong>RUN WITH IT</strong></p>\n<p>- High-waisted<br>- Compressive fit</p>"

        val cleaned = cleanDescriptionHtml(html)

        assertEquals(html, cleaned)
    }

    @Test
    fun `returns an empty string for blank input`() {
        val html = "  \n "

        val cleaned = cleanDescriptionHtml(html)

        assertEquals("", cleaned)
    }

    @Test
    fun `cleans a real description from the payload`() {
        val html = REAL_DESCRIPTION

        val cleaned = cleanDescriptionHtml(html)

        assertEquals(REAL_DESCRIPTION_CLEANED, cleaned)
    }
}

private val nbsp = "\u00A0"

private val REAL_DESCRIPTION = """<p><strong>RUN WITH IT</strong></p>
<p><br data-mce-fragment="1">Your run requires enduring comfort and support, so step out and hit the road in Speed. Made with zero-distractions and lightweight, ventilating fabrics that move with you, you can trust in Speed no matter how far you go.</p>
<p>${nbsp}</p>
<p><br data-mce-fragment="1">- Full length legging<br data-mce-fragment="1">- High-waisted<br data-mce-fragment="1">- Compressive fit<br data-mce-fragment="1">- Internal adjustable elastic/drawcord at front waistband<br data-mce-fragment="1">- Pocket to back of waistband<br data-mce-fragment="1">- Reflective Gymshark sharkhead logo to ankle<br data-mce-fragment="1">- Main: 88% Polyester 12% Elastane. Internal Mesh: 76% Nylon 24% Elastane<br data-mce-fragment="1">- We've cut down our use of swing tags, so this product comes without one<br data-mce-fragment="1">- Model is${nbsp}<meta charset="utf-8"><span data-usefontface="true" data-contrast="none" class="TextRun SCXP103297068 BCX0" lang="EN-GB" data-mce-fragment="1" xml:lang="EN-GB"><span class="NormalTextRun SCXP103297068 BCX0" data-mce-fragment="1">5'3" and wears a size M</span></span><br>- SKU:${nbsp}B3A3E-BBBB</p>"""

private val REAL_DESCRIPTION_CLEANED = """<p><strong>RUN WITH IT</strong></p>
<p>Your run requires enduring comfort and support, so step out and hit the road in Speed. Made with zero-distractions and lightweight, ventilating fabrics that move with you, you can trust in Speed no matter how far you go.</p>
<p>- Full length legging<br data-mce-fragment="1">- High-waisted<br data-mce-fragment="1">- Compressive fit<br data-mce-fragment="1">- Internal adjustable elastic/drawcord at front waistband<br data-mce-fragment="1">- Pocket to back of waistband<br data-mce-fragment="1">- Reflective Gymshark sharkhead logo to ankle<br data-mce-fragment="1">- Main: 88% Polyester 12% Elastane. Internal Mesh: 76% Nylon 24% Elastane<br data-mce-fragment="1">- We've cut down our use of swing tags, so this product comes without one<br data-mce-fragment="1">- Model is${nbsp}<span data-usefontface="true" data-contrast="none" class="TextRun SCXP103297068 BCX0" lang="EN-GB" data-mce-fragment="1" xml:lang="EN-GB"><span class="NormalTextRun SCXP103297068 BCX0" data-mce-fragment="1">5'3" and wears a size M</span></span><br>- SKU:${nbsp}B3A3E-BBBB</p>"""
