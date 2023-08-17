package com.memorati.core.design.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.memorati.core.design.R
import com.memorati.core.design.icon.MemoratiIcons

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    favoured: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconToggleButton(
        modifier = modifier,
        checked = favoured,
        onCheckedChange = onCheckedChange,
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
