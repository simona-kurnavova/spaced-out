package com.kurnavova.spacedout.features.newslist.ui.components.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.components.image.CircularIcon
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ArticleNonExpanded(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        CircularIcon(
            iconRes = R.drawable.ic_rocket,
            contentDescription = null,
            modifier = Modifier.align(Alignment.Top)
        )

        Spacer(modifier = Modifier.width(SPACER_SIZE.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

private const val SPACER_SIZE = 12

@Preview(showBackground = true)
@Composable
private fun ArticleNonExpandedPreview() {
    SpacedOutTheme {
        ArticleNonExpanded(
            title = "Article title",
        )
    }
}
