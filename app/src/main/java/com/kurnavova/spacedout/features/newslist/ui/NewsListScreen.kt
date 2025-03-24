package com.kurnavova.spacedout.features.newslist.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    NewsListScreen()
}

@Composable
private fun NewsListScreen() {
    Text(text = "News List Screen")
}

@Preview
@Composable
private fun NewsListPreview() {
    SpacedOutTheme {
        NewsListScreen()
    }
}
