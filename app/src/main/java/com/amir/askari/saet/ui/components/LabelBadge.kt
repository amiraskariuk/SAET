package com.amir.askari.saet.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amir.askari.saet.shared.domain.LabelStyle

@Composable
fun LabelBadge(
    text: String,
    style: LabelStyle,
    modifier: Modifier = Modifier,
) {
    val colours = MaterialTheme.colorScheme
    val (containerColour, contentColour) = when (style) {
        LabelStyle.Urgent -> colours.errorContainer to colours.onErrorContainer
        LabelStyle.Highlight -> colours.inverseSurface to colours.inverseOnSurface
        LabelStyle.Sustainable -> colours.tertiaryContainer to colours.onTertiaryContainer
        LabelStyle.Neutral -> colours.surfaceVariant to colours.onSurfaceVariant
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = containerColour,
        contentColor = contentColour,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}
