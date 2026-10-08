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
import com.amir.askari.saet.ui.theme.BadgeContent
import com.amir.askari.saet.ui.theme.BadgeHighlight
import com.amir.askari.saet.ui.theme.BadgeNeutral
import com.amir.askari.saet.ui.theme.BadgeSustainable
import com.amir.askari.saet.ui.theme.BadgeUrgent

@Composable
fun LabelBadge(
    text: String,
    style: LabelStyle,
    modifier: Modifier = Modifier,
) {
    val containerColour = when (style) {
        LabelStyle.Urgent -> BadgeUrgent
        LabelStyle.Highlight -> BadgeHighlight
        LabelStyle.Sustainable -> BadgeSustainable
        LabelStyle.Neutral -> BadgeNeutral
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = containerColour,
        contentColor = BadgeContent,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}
