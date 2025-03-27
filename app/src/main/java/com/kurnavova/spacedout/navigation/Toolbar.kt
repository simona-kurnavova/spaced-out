package com.kurnavova.spacedout.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.preview.ComponentPreview
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    toolbarState: ToolbarState,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = stringResource(toolbarState.title)) },
        navigationIcon = {
            if (toolbarState.backActionAvailable) {
                IconButton(onClick = popBackStack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        modifier = modifier
    )
}

/**
 * Represents the state of the toolbar.
 *
 * @property title The title of the toolbar.
 * @property backActionAvailable Whether the back button action is available.
 */
@Stable
data class ToolbarState(
    @StringRes
    val title: Int,
    val backActionAvailable: Boolean
)

@ComponentPreview
@Composable
private fun ToolbarPreview() {
    SpacedOutTheme {
        Toolbar(
            toolbarState = ToolbarState(R.string.app_name, false),
            popBackStack = {}
        )
    }
}
