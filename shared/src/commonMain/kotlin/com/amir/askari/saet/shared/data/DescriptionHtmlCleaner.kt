package com.amir.askari.saet.shared.data

private val metaTag = Regex("<meta[^>]*>", RegexOption.IGNORE_CASE)

private val breaksAtParagraphStart = Regex("(<p(\\s[^>]*)?>)(\\s*<br[^>]*>)+", RegexOption.IGNORE_CASE)

private val emptyParagraph = Regex("<p(\\s[^>]*)?>(\\s| |&nbsp;|<br[^>]*>)*</p>\\s*", RegexOption.IGNORE_CASE)

fun cleanDescriptionHtml(html: String): String =
    html
        .replace(metaTag, "")
        .replace(breaksAtParagraphStart, "$1")
        .replace(emptyParagraph, "")
        .trim()
