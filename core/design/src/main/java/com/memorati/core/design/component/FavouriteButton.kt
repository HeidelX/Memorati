package com.memorati.core.design.component

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.memorati.core.design.R

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    favoured: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    FilledIconToggleButton(
        modifier = modifier,
        checked = favoured,
        onCheckedChange = onCheckedChange,
        colors = IconButtonDefaults.iconToggleButtonColors(
            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = if (favoured) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.12f,
                )
            } else {
                Color.Transparent
            },
        ),
    ) {
        Icon(
            imageVector = if (favoured) {
                MemoratiIcons.Favourites
            } else {
                MemoratiIcons.FavouritesBorder
            },
            contentDescription = if (favoured) {
                stringResource(id = R.string.remove_from_favourites)
            } else {
                stringResource(id = R.string.add_to_favourites)
            },
        )
    }
}
