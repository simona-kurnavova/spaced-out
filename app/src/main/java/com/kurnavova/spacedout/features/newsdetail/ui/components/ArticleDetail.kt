package com.kurnavova.spacedout.features.newsdetail.ui.components

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.domain.usecase.model.Article
import com.kurnavova.spacedout.ui.components.image.CoilImage
import com.kurnavova.spacedout.ui.components.text.ClickableText
import com.kurnavova.spacedout.ui.components.text.HighlightedText
import com.kurnavova.spacedout.ui.components.text.FadedSubtitle
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import androidx.core.net.toUri

@Composable
fun ArticleDetail(
    article: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        CoilImage(
            url = article.imageUrl,
            modifier = Modifier
                .padding(bottom = IMAGE_PADDING.dp)
                .align(Alignment.CenterHorizontally)
        )

        HighlightedText(
            text = article.title,
            modifier = Modifier.fillMaxWidth()
        )

        FadedSubtitle(
            text = stringResource(
                R.string.news_detail_authors_datetime,
                article.authors,
                article.publishedAt
            ),
            modifier = Modifier.padding(vertical = AUTHOR_PADDING.dp)
        )

        Spacer(modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp))

        Text(
            text = article.summary,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp))

        ClickableText(
            text = stringResource(R.string.news_detail_read_article_here),
            onClick = {
                val customTabsIntent = CustomTabsIntent.Builder().build()
                customTabsIntent.launchUrl(context, article.url.toUri())
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(SPACE_UNDER_BUTTON.dp))

        Text(
            text = stringResource(R.string.news_site_label, article.newsSite),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private const val SPACE_BETWEEN_ITEMS = 16
private const val SPACE_UNDER_BUTTON = 8
private const val AUTHOR_PADDING = 8
private const val IMAGE_PADDING = 8

@Preview(showBackground = true)
@Composable
private fun ArticleDetailPreview() {
    SpacedOutTheme {
        ArticleDetail(
            article = Article(
                id = 1,
                title = "Article title",
                summary = "Article content",
                imageUrl = "https://example.com/image.jpg",
                authors = "Author",
                publishedAt = "2022-01-01",
                url = "https://example.com",
                newsSite = "NASA"
            )
        )
    }
}