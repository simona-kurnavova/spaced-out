package com.kurnavova.spacedout.features.newslist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.domain.usecase.model.Article
import com.kurnavova.spacedout.features.newslist.ui.components.item.ArticleCard
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ListArticle(
    article: Article,
    showDivider: Boolean,
    showDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ArticleCard(
            title = article.title,
            summary = article.summary,
            onReadMoreAction = showDetail,
            modifier = Modifier.fillMaxWidth()
        )

        if (showDivider) {
            Spacer(modifier = Modifier.height(DIVIDER_HEIGHT.dp))
        }
    }
}

private const val DIVIDER_HEIGHT = 12

@Preview
@Composable
private fun ListArticlePreview() {
    SpacedOutTheme {
        ListArticle(
            article = Article(
                id = 1,
                title = "Article title",
                summary = "Article content",
                imageUrl = "https://www.nasa.gov/wp-content/uploads/2025/03/lrc-2025-ocio-p-00643-3.jpg",
                url = "http://example.com",
                authors = "Author 1, Author 2",
                publishedAt = "02/03/1992",
                newsSite = "NASA"
            ),
            showDivider = true,
            showDetail = {}
        )
    }
}