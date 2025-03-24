package com.kurnavova.spacedout.features.newslist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.features.newslist.ui.components.ArticleCard
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    NewsListScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(),
        onAction = { action ->
            when (action) {
                is NewsListAction.ShowDetail -> navigateToDetail(action.id)
                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
private fun NewsListScreen(
    uiState: State<NewsListUiState>,
    onAction: (NewsListAction) -> Unit,
) {
    val status = uiState.value.loadingState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SCREEN_PADDING.dp)
    ) {
        when(status) {
            is LoadingState.Loading ->
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

            is LoadingState.Error ->
                Text(
                    text = stringResource(id = status.message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )

            is LoadingState.Loaded ->
                LazyColumn {
                    items(status.articles.size) { index ->
                        val article = status.articles[index]
                        ArticleCard(
                            title = article.title,
                            summary = article.summary,
                            onReadMoreAction = { onAction(NewsListAction.ShowDetail(article.id)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (index < status.articles.size - 1) {
                            // Add some space between items
                            Spacer(modifier = Modifier.height(SPACE_BETWEEN_ITEMS.dp))
                        }
                    }
                }

            is LoadingState.Idle -> Unit // Just wait
        }
    }
}

private const val SCREEN_PADDING = 12
private const val SPACE_BETWEEN_ITEMS = 12

@Preview
@Composable
private fun NewsListPreview() {
    val article = Article(
        id = 1,
        title = "Article title",
        summary = "Article summary",
    )
    val state = remember {
        mutableStateOf(NewsListUiState(
            loadingState = LoadingState.Loaded(
                articles = persistentListOf(article, article, article, article)
            )
        ))
    }

    SpacedOutTheme {
        NewsListScreen(
            uiState = state,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun NewsListErrorPreview() {
    val state = remember {
        mutableStateOf(NewsListUiState(
            loadingState = LoadingState.Error(
               message = R.string.error_server
            )
        ))
    }

    SpacedOutTheme {
        NewsListScreen(
            uiState = state,
            onAction = {}
        )
    }
}
