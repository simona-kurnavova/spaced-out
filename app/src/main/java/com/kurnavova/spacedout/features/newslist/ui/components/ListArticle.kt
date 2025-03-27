package com.kurnavova.spacedout.features.newslist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kurnavova.spacedout.domain.usecase.model.Article
import com.kurnavova.spacedout.features.newslist.ui.components.item.ArticleCard
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ListArticle(
    article: Article,
    showDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    ArticleCard(
        title = article.title,
        summary = article.summary,
        onReadMoreAction = showDetail,
        modifier = modifier.fillMaxWidth()
    )
}

@ComponentPreview
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
                publishedAt = "02/03/1992"
            ),
            showDetail = {}
        )
    }
}