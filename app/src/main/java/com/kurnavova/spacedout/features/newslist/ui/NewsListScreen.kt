package com.kurnavova.spacedout.features.newslist.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.domain.usecase.model.Article
import com.kurnavova.spacedout.features.newslist.ui.components.ErrorState
import com.kurnavova.spacedout.features.newslist.ui.components.ListArticle
import com.kurnavova.spacedout.features.newslist.ui.components.OfflineBanner
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
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SCREEN_PADDING.dp)
    ) {
        when(articles.loadState.refresh) {
            // Data are being loaded, show loading in the middle of the screen.
             is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                return@Column
            }
            is LoadState.Error -> if (articles.itemCount > 0) {
                // There was an error in loading the data, but we are still showing some data.
                OfflineBanner(refresh = articles::refresh)
            } else {
                // There was an error loading the data and there is no data to show.
                ErrorState(
                    message = stringResource(R.string.load_data_failed),
                    retry = articles::refresh,
                    modifier = Modifier
                        .padding(vertical = SPACE_BETWEEN_ITEMS.dp)
                        .fillMaxWidth()
                )
                return@Column
            }
            else -> Unit
        }

        LazyColumn {
            items(articles.itemCount) { index ->
                articles[index]?.let {
                    key(it.id) {
                        ListArticle(
                            article = it,
                            showDivider = index < articles.itemCount - 1,
                            showDetail = { onAction(NewsListAction.ShowDetail(it.id)) }
                        )
                    }
                }
            }

            item {
                AnimatedVisibility(visible = articles.loadState.append is LoadState.Loading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(vertical = LOADING_PADDING.dp)
                        )
                    }
                }
            }

            if (articles.loadState.append is LoadState.Error) {
                item { // Append failed, show error state at the end of the list
                    ErrorState(
                        message = stringResource(R.string.connect_and_try_again),
                        retry = articles::retry,
                        modifier = Modifier.padding(vertical = SPACE_BETWEEN_ITEMS.dp)
                    )
                }
            }
        }
    }
}

private const val SCREEN_PADDING = 12
private const val SPACE_BETWEEN_ITEMS = 12
private const val LOADING_PADDING = 18

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
