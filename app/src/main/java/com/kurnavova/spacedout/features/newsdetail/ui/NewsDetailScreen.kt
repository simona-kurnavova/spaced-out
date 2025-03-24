package com.kurnavova.spacedout.features.newsdetail.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailScreenRoot(
    id: Int,
    viewModel: NewsDetailViewModel = koinViewModel()
) {
    NewsDetailScreen()
}

@Composable
private fun NewsDetailScreen() {
    Text(text = "News Detail Screen")
}

@Preview
@Composable
private fun NewsDetailScreenPreview() {
    SpacedOutTheme {
        NewsDetailScreen()
    }
}
