package com.kurnavova.spacedout.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun FadedSubtitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(
            alpha = ALPHA_TEXT
        ),
        modifier = modifier
    )
}

private const val ALPHA_TEXT = 0.6f

@Preview(showBackground = true)
@Composable
private fun FadedSubtitlePreview() {
    SpacedOutTheme {
        FadedSubtitle(text = "Some subtitle text")
    }
}
