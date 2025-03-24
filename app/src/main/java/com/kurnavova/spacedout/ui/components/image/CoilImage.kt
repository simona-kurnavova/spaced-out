package com.kurnavova.spacedout.ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun CoilImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Preview
@Composable
private fun CoilImagePreview() {
    SpacedOutTheme {
        CoilImage(
            url = "https://www.example.com/image.jpg",
        )
    }
}
