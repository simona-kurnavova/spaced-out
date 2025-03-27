package com.kurnavova.spacedout.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun HighlightedText(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(TITLE_INSIDE_PADDING.dp)
        )
    }
}

private const val TITLE_INSIDE_PADDING = 8

@ComponentPreview
@Composable
private fun HighlightedTextPreview() {
    SpacedOutTheme {
        HighlightedText(
            text = "Hello world!"
        )
    }
}
