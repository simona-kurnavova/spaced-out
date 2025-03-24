package com.kurnavova.spacedout.ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun CoilImage(
    url: String,
    maxSize: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val painter = rememberAsyncImagePainter(
        url,
        placeholder = painterResource(R.drawable.ic_rocket)
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .heightIn(max = maxSize.dp)
            .widthIn(max = maxSize.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun CoilImagePreview() {
    SpacedOutTheme {
        CoilImage(
            url = "https://www.example.com/image.jpg",
            maxSize = 100
        )
    }
}
