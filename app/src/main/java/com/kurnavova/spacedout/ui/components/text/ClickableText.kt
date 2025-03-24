package com.kurnavova.spacedout.ui.components.text

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ClickableText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Int = ITEM_CORNER_RADIUS
) {
    val shape = RoundedCornerShape(cornerRadius.dp)

    Box(
        modifier = modifier
            .clip(shape = shape)
            .border(
                width = BORDER_WIDTH.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = shape
            )
            .clickable(
                onClick = { onClick() }
            )
            .padding(INSIDE_PADDING.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private const val ITEM_CORNER_RADIUS = 12
private const val INSIDE_PADDING = 12
private const val BORDER_WIDTH = 1

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ClickableTextPreview() {
    SpacedOutTheme {
        ClickableText(
            text = "Clickable text",
            onClick = {},
            modifier = Modifier
        )
    }
}
