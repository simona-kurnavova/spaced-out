package com.kurnavova.spacedout.features.newsdetail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.features.newsdetail.ui.ArticleUiDetail
import com.kurnavova.spacedout.ui.components.image.CoilImage
import com.kurnavova.spacedout.ui.components.text.ClickableText
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import com.kurnavova.spacedout.ui.utils.IntentUtils

@Composable
fun ArticleDetail(
    article: ArticleUiDetail,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        CoilImage(
            url = article.imageUrl,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp))

        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(TITLE_CORNER_RADIUS.dp))
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
                .padding(TITLE_INSIDE_PADDING.dp)
        )

        Text(
            text = stringResource(
                R.string.news_detail_authors_datetime,
                article.authors,
                article.publishedAt
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = AUTHORS_ALPHA_TEXT
            ),
            modifier = Modifier.padding(vertical = AUTHOR_PADDING.dp)
        )

        Spacer(modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp))

        Text(
            text = article.summary,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp))

        ClickableText(
            text = stringResource(R.string.news_detail_read_article_here),
            onClick = { IntentUtils.openBrowser(article.url, context) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private const val SPACE_BETWEEN_ITEMS = 16
private const val AUTHORS_ALPHA_TEXT = 0.6f
private const val TITLE_CORNER_RADIUS = 8
private const val AUTHOR_PADDING = 8
private const val TITLE_INSIDE_PADDING = 8

@Preview(showBackground = true)
@Composable
private fun ArticleDetailPreview() {
    SpacedOutTheme {
        ArticleDetail(
            article = ArticleUiDetail(
                title = "Article title",
                summary = "Article content",
                imageUrl = "https://example.com/image.jpg",
                authors = "Author",
                publishedAt = "2022-01-01",
                url = "https://example.com"
            )
        )
    }
}