package com.kurnavova.spacedout.features.newsdetail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurnavova.spacedout.domain.usecase.model.Article
import com.kurnavova.spacedout.features.newsdetail.ui.components.ArticleDetail
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailScreenRoot(
    id: Int,
    viewModel: NewsDetailViewModel = koinViewModel()
) {
    LaunchedEffect(id) {
        viewModel.onAction(NewsDetailAction.FetchArticle(id))
    }

    NewsDetailScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle()
    )
}

@Composable
private fun NewsDetailScreen(
    uiState: State<NewsDetailUiState>,
) {
    val status = uiState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SCREEN_PADDING.dp)
    ) {
        when (status) {
            is NewsDetailUiState.Idle -> Unit
            is NewsDetailUiState.Loading ->
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            
            is NewsDetailUiState.Error -> Text(
                text = stringResource(id = status.message),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
            is NewsDetailUiState.Loaded ->
                ArticleDetail(status.article)
        }
    }
}

private const val SCREEN_PADDING = 12

@ComponentPreview
@Composable
private fun NewsDetailScreenPreview() {
    val state = remember {
        mutableStateOf(
            NewsDetailUiState.Loaded(
                Article(
                    id = 1,
                    title = "Article title",
                    summary = "Article content",
                    imageUrl = "https://www.nasa.gov/wp-content/uploads/2025/03/lrc-2025-ocio-p-00643-3.jpg",
                    url = "http://example.com",
                    authors = "Author 1, Author 2",
                    publishedAt = "02/03/1992"
                )
            )
        )
    }

    SpacedOutTheme {
        NewsDetailScreen(
            uiState = state,
        )
    }
}
