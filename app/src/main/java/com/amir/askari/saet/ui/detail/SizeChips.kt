package com.amir.askari.saet.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.amir.askari.saet.R
import com.amir.askari.saet.shared.domain.ProductSize

@Composable
fun SizeChips(
    sizes: List<ProductSize>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        sizes.forEach { size -> SizeChip(size) }
    }
}

@Composable
private fun SizeChip(size: ProductSize) {
    val soldOutDescription = stringResource(R.string.size_sold_out, size.name)
    val semanticsModifier = if (size.inStock) {
        Modifier
    } else {
        Modifier.clearAndSetSemantics { contentDescription = soldOutDescription }
    }
    val colours = MaterialTheme.colorScheme

    Surface(
        modifier = semanticsModifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (size.inStock) colours.outline else colours.outlineVariant),
    ) {
        Text(
            text = size.name,
            style = MaterialTheme.typography.labelLarge,
            color = if (size.inStock) colours.onSurface else colours.onSurface.copy(alpha = 0.38f),
            textDecoration = if (size.inStock) null else TextDecoration.LineThrough,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
        )
    }
}
