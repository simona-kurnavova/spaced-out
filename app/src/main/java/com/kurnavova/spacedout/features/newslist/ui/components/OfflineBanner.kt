package com.kurnavova.spacedout.features.newslist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.components.card.AlertBanner
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun OfflineBanner(
    refresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertBanner(
        text = stringResource(R.string.offline_message),
        buttonText = stringResource(id = R.string.try_again_button),
        onButtonClick = refresh,
        modifier = modifier
            .padding(bottom = BANNER_PADDING.dp)
            .fillMaxWidth()
    )
}

private const val BANNER_PADDING = 12

@ComponentPreview
@Composable
private fun OfflineBannerPreview() {
    SpacedOutTheme {
        OfflineBanner(refresh = {})
    }
}
