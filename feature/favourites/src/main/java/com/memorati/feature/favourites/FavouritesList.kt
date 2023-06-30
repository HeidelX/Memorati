package com.memorati.feature.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.provider.FlashcardsProvider

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun FavouritesList(
    cards: List<Flashcard>,
    toggleFavoured: (Flashcard) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),

    ) {
        items(cards, key = { it.id }) { card ->
            CardItem(
                card = card,
                toggleFavoured = toggleFavoured,
                modifier = Modifier.animateItemPlacement(),
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        Surface(
            modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary,
        ) {
            Box(modifier = Modifier) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = card.front,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 2,
                    )

                    Divider(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(vertical = 10.dp),
                    )
                    Text(
                        text = card.back,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                    )
                }

                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable {
                            toggleFavoured(card)
                        },
                    imageVector = if (card.favoured) {
                        Icons.Rounded.Favorite
                    } else {
                        Icons.Rounded.FavoriteBorder
                    },
                    contentDescription = "",
                )
            }
        }
    }
}

@DevicePreviews
@Composable
internal fun CardsScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    FavouritesScreen(
        cards = flashcards,
        toggleFavoured = {},
    )
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
