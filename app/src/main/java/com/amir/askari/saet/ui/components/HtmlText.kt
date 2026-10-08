package com.amir.askari.saet.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier,
) {
    val text = remember(html) { AnnotatedString.fromHtml(html) }
    Text(text = text, style = MaterialTheme.typography.bodyMedium, modifier = modifier)
}
