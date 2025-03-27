package com.kurnavova.spacedout.ui.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.preview.ComponentPreview

@Composable
fun FavouriteButton(
    isChecked: Boolean,
    onCheck: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {
            onCheck(!isChecked)
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isChecked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isChecked) {
                stringResource(R.string.favourite_button_remove_descr)
            } else {
                stringResource(R.string.favourite_button_add_descr)
            },
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@ComponentPreview
@Composable
fun FavouriteButtonPreview() {
    var isFavourite by remember { mutableStateOf(false) }

    FavouriteButton(
        isChecked = isFavourite,
        onCheck = { isFavourite = it },
    )
}
