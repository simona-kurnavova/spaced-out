package com.kurnavova.spacedout.features.newslist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurnavova.spacedout.domain.usecase.model.Article
import com.kurnavova.spacedout.features.newslist.ui.components.item.ArticleCard
import com.kurnavova.spacedout.features.newslist.ui.components.ErrorState
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    NewsListScreen(
        articles = viewModel.uiState.collectAsLazyPagingItems(),
        onAction = { action ->
            when (action) {
                is NewsListAction.ShowDetail -> navigateToDetail(action.id)
            }
        }
    )
}

@Composable
private fun NewsListScreen(
    articles: LazyPagingItems<Article>,
    onAction: (NewsListAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SCREEN_PADDING.dp)
    ) {
        if (articles.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            return@Box
        }

        LazyColumn {
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    Column {
                        ArticleCard(
                            title = article.title,
                            summary = article.summary,
                            onReadMoreAction = { onAction(NewsListAction.ShowDetail(article.id)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        when {
                            index < articles.itemCount - 1 -> Spacer(
                                modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp)
                            )

                            articles.loadState.append is LoadState.Loading -> CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = SPACE_BETWEEN_ITEMS.dp)
                            )
                        }
                    }
                }
            }

            if (articles.loadState.hasError) {
                item {
                    ErrorState(retry = { articles.retry() })
                }
            }
        }
    }
}

private const val SCREEN_PADDING = 12
private const val SPACE_BETWEEN_ITEMS = 12

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun NewsListPreview() {
    val article = Article(
        id = 1,
        title = "Article title",
        summary = "Article summary",
        imageUrl = "https://example.com/image.jpg",
        url = "https://example.com",
        authors = "Author 1, Author 2",
        publishedAt = "02/03/1992"
    )

    val articles = flow {
        emit(PagingData.from(listOf(article, article, article)))
    }.collectAsLazyPagingItems()

    SpacedOutTheme {
        NewsListScreen(
            articles = articles,
            onAction = {}
        )
    }
}
