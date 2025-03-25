package com.kurnavova.spacedout.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun HighlightedText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
            )
            .padding(TITLE_INSIDE_PADDING.dp)
    )
}

private const val TITLE_INSIDE_PADDING = 8

@Preview(showBackground = true)
@Composable
private fun HighlightedTextPreview() {
    SpacedOutTheme {
        HighlightedText(
            text = "Hello world!"
        )
    }
}
