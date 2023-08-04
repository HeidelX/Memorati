package com.memorati.feature.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FavouriteButton
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.provider.FlashcardsProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun FavouritesList(
    cards: List<Flashcard>,
    toggleFavoured: (Flashcard) -> Unit,
    modifier: Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),
    ) {
        items(cards, key = { it.id }) { card ->
            CardItem(
                modifier = Modifier
                    .padding(3.dp)
                    .animateItemPlacement(),
                card = card,
                toggleFavoured = toggleFavoured,
            )
            Spacer(modifier = modifier.height(10.dp))
        }
    }
}

@Composable
internal fun CardItem(
    card: Flashcard,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .defaultMinSize(minHeight = 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = card.idiom,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 3,
                )

                HorizontalDivider(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(vertical = 10.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                )

                Text(
                    text = card.meaning,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                )
            }

            FavouriteButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                favoured = card.favoured,
                onCheckedChange = {
                    toggleFavoured(card)
                },
            )
        }
    }
}

@DevicePreviews
@Composable
internal fun CardsScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    MemoratiTheme {
        FavouritesScreen(
            cards = flashcards,
            toggleFavoured = {},
        )
    }
}

@DevicePreviews
@Composable
internal fun CardItemPreview(
    @PreviewParameter(FlashcardProvider::class) flashcard: Flashcard,
) {
    CardItem(
        card = flashcard,
        toggleFavoured = {},
    )
}
