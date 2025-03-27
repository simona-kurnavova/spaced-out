package com.kurnavova.spacedout.features.newslist.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.features.ui.model.Article
import com.kurnavova.spacedout.features.newslist.ui.components.ErrorState
import com.kurnavova.spacedout.features.newslist.ui.components.ListArticle
import com.kurnavova.spacedout.features.newslist.ui.components.OfflineBanner
import com.kurnavova.spacedout.features.newslist.ui.utils.isAppendError
import com.kurnavova.spacedout.features.newslist.ui.utils.isAppendLoad
import com.kurnavova.spacedout.features.newslist.ui.utils.isInitialLoad
import com.kurnavova.spacedout.features.newslist.ui.utils.isInitialLoadError
import com.kurnavova.spacedout.features.newslist.ui.utils.isOfflineWithData
import com.kurnavova.spacedout.features.newslist.ui.utils.isRefreshing
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import kotlinx.coroutines.flow.MutableStateFlow
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
        TopLevelStatusIndicator(
            isInitialLoadError = articles.isInitialLoadError,
            isInitialLoad = articles.isInitialLoad,
            isOfflineWithData = articles.isOfflineWithData,
            refresh = articles::refresh,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = articles.isRefreshing),
            swipeEnabled = true,
            onRefresh = { articles.refresh() }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ARTICLES.dp),
            ) {
                items(articles.itemCount) { index ->
                    articles[index]?.let {
                        ListArticle(
                            article = it,
                            showDetail = { onAction(NewsListAction.ShowDetail(it.id)) }
                        )
                    }
                }

                item {
                    AnimatedVisibility(visible = articles.isAppendLoad) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(vertical = LOADING_PADDING.dp)
                            )
                        }
                    }
                }

                if (articles.isAppendError) {
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
}

private const val SCREEN_PADDING = 12
private const val SPACE_BETWEEN_ARTICLES = 8
private const val SPACE_BETWEEN_ITEMS = 12
private const val LOADING_PADDING = 18

@Composable
private fun TopLevelStatusIndicator(
    isInitialLoadError: Boolean,
    isInitialLoad: Boolean,
    isOfflineWithData: Boolean,
    refresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        // There was an error loading the data and there is no data to show.
        isInitialLoadError -> {
            ErrorState(
                message = stringResource(R.string.load_data_failed),
                retry = refresh,
                modifier = modifier
                    .padding(vertical = SPACE_BETWEEN_ITEMS.dp)
                    .fillMaxWidth()
            )
        }

        // Data are being loaded, show loading in the middle of the screen.
        isInitialLoad -> {
            CircularProgressIndicator(modifier = modifier.padding(vertical = SPACE_BETWEEN_ITEMS.dp))
        }

        // There was an error in loading the data, but we are still showing some data.
        isOfflineWithData ->
            OfflineBanner(
                refresh = refresh,
                modifier = modifier
            )
    }
}

@ComponentPreview
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

    val articles = MutableStateFlow(PagingData.from(List(3) { article.copy(id = it) }))
        .collectAsLazyPagingItems()

    SpacedOutTheme {
        NewsListScreen(
            articles = articles,
            onAction = {}
        )
    }
}
