package com.kurnavova.spacedout.features.favourites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.features.ui.components.ErrorState
import com.kurnavova.spacedout.features.newslist.ui.components.ListArticle
import com.kurnavova.spacedout.features.ui.model.Article
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteArticlesScreenRoot(
    viewModel: FavouriteArticlesViewModel = koinViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    FavouriteArticlesScreen(
        articles = viewModel.uiState.collectAsLazyPagingItems(),
        onAction = { action ->
            when (action) {
                is FavouriteAction.ShowDetail -> navigateToDetail(action.id)
            }
        }
    )
}

@Composable
private fun FavouriteArticlesScreen(
    articles: LazyPagingItems<Article>,
    onAction: (FavouriteAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SCREEN_PADDING.dp)
    ) {
        // Show loading indicator when data is being fetched
        if (articles.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SPACE_BETWEEN_ITEMS.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        if (articles.loadState.refresh is LoadState.Error) {
            ErrorState(
                message = stringResource(R.string.favourites_load_failed),
                retry = { articles.refresh() },
                modifier = Modifier
                    .padding(vertical = SPACE_BETWEEN_ITEMS.dp)
                    .fillMaxWidth()
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ARTICLES.dp)) {
            items(articles.itemCount) { index ->
                articles[index]?.let {
                    key(it.id) { // Key so that UI remembers expanded state of card for the article, not position.
                        ListArticle(
                            article = it,
                            showDetail = { onAction(FavouriteAction.ShowDetail(it.id)) }
                        )
                    }
                }
            }
        }

        // If no articles are available, show  message
        if (articles.itemCount == 0 && articles.loadState.refresh is LoadState.NotLoading) {
            Text(
                text = stringResource(R.string.favourites_no_articles),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SPACE_BETWEEN_ITEMS.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

private const val SCREEN_PADDING = 12
private const val SPACE_BETWEEN_ITEMS = 8
private const val SPACE_BETWEEN_ARTICLES = 8

@ComponentPreview
@Composable
private fun FavouriteArticlesScreenPreview() {
    val article = Article(
        id = 1,
        title = "Article title",
        summary = "Article summary",
        imageUrl = "https://example.com/image.jpg",
        url = "https://example.com",
        authors = "Author 1, Author 2",
        publishedAt = "02/03/1992",
        isFavourite = false
    )

    val articles = MutableStateFlow(PagingData.from(List(3) { article.copy(id = it) }))
        .collectAsLazyPagingItems()

    FavouriteArticlesScreen(
        articles = articles,
        onAction = {}
    )
}
