package com.memorati.feature.cards

import MemoratiIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.CardMembership
import com.memorati.core.ui.DevicePreviews

@Composable
fun EmptyCardsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(ratio = 1f)
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .width(200.dp),
            imageVector = MemoratiIcons.CardMembership,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary.copy(0.5f)),
        )

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = stringResource(id = R.string.no_cards_message),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@DevicePreviews
@Composable
fun EmptyCardsScreenPreview() {
    EmptyCardsScreen()
}
