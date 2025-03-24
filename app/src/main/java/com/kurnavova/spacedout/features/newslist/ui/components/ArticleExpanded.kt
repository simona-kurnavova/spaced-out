package com.kurnavova.spacedout.features.newslist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.ui.components.text.ClickableText
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ArticleExpanded(
    title: String,
    summary: String,
    onReadMoreAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = DIVIDER_PADDING.dp))

        Text(
            text = summary,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = SUBTITLE_ALPHA
            )
        )

        Spacer(modifier = Modifier.height(SPACER_BEFORE_ACTION.dp))

        ClickableText(
            text = "Read more",
            onClick = onReadMoreAction,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

private const val DIVIDER_PADDING = 12
private const val SPACER_BEFORE_ACTION = 12
private const val SUBTITLE_ALPHA = 0.6f

@Preview(showBackground = true)
@Composable
private fun ArticleExpandedPreview() {
    SpacedOutTheme {
        ArticleExpanded(
            title = "Article title",
            summary = "Article summary, so this might be some long text.",
            onReadMoreAction = {}
        )
    }
}