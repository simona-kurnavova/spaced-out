package com.kurnavova.spacedout.ui.components.card

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

@Composable
fun ExpandableCard(
    nonExpandedContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = !expanded }),
    ) {
        Box(modifier = Modifier.padding(INSIDE_PADDING.dp)) {
            if (expanded) {
                expandedContent()
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        nonExpandedContent()
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.expand_card_action),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .clickable(onClick = { expanded = !expanded })
                    )
                }
            }
        }
    }
}

private const val INSIDE_PADDING = 16

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ExpandableCardPreview() {
    SpacedOutTheme {
        ExpandableCard(
            nonExpandedContent = {
                Text(
                    text = "Non expanded card",
                )
            },
            expandedContent = {
                Text(
                    text = "Expanded card",
                )
            }
        )
    }
}
