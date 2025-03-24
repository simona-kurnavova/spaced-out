package com.kurnavova.spacedout.features.newslist.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kurnavova.spacedout.ui.components.card.ExpandableCard
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ArticleCard(
    title: String,
    summary: String,
    onReadMoreAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExpandableCard(
        nonExpandedContent = {
            ArticleNonExpanded(title = title)
        },
        expandedContent = {
            ArticleExpanded(
                title = title,
                summary = summary,
                onReadMoreAction = onReadMoreAction,
            )
        },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun ArticleCardPreview() {
    SpacedOutTheme {
        ArticleCard(
            title = "Article title",
            summary = "Article summary, so this might be some long text.",
            onReadMoreAction = {}
        )
    }
}
