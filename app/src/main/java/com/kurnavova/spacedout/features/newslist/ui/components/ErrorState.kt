package com.kurnavova.spacedout.features.newslist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.components.text.ClickableText
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ErrorState(
    retry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.connect_and_try_again),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .padding(top = SPACE_BETWEEN_ITEMS.dp)
                .align(Alignment.CenterHorizontally)
        )

        ClickableText(
            text = stringResource(id = R.string.try_again_button),
            onClick = retry,
            modifier = Modifier
                .padding(top = SPACE_BETWEEN_ITEMS.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

private const val SPACE_BETWEEN_ITEMS = 12

@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    SpacedOutTheme {
        ErrorState(
            retry = {}
        )
    }
}