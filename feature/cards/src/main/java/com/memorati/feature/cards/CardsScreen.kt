package com.memorati.feature.cards

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import kotlinx.datetime.Clock

@Composable
fun CardsRoute(
    modifier: Modifier = Modifier,
    viewModel: CardsViewModel = hiltViewModel(),
) {
    val cards by viewModel.cards.collectAsState(initial = listOf())
    CardsScreen(cards, modifier) { card ->
        viewModel.toggleFavoured(card)
    }
}

@Composable
internal fun CardsScreen(
    cards: List<Flashcard>,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),

    ) {
        items(cards) { card ->
            CardItem(card, toggleFavoured = toggleFavoured)
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
                .padding(30.dp),
            color = MaterialTheme.colorScheme.primary,
        ) {
            Box(modifier = modifier) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize(),
                ) {
                    Text(
                        text = card.front,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                    )

                    Divider(
                        modifier = modifier
                            .width(150.dp)
                            .padding(vertical = 10.dp),
                    )
                    Text(
                        text = card.back,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                Icon(
                    modifier = modifier
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
internal fun CardsScreenPreview() {
    CardsScreen(
        cards = listOf(
            Flashcard(
                id = 1,
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                id = 2,
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
                favoured = true,
            ),
            Flashcard(
                id = 3,
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
        ),
        toggleFavoured = {},
    )
}

@DevicePreviews
@Composable
internal fun CardItemPreview() {
    CardItem(
        card = Flashcard(
            id = 1,
            front = "Hello",
            back = "Hallo",
            createdAt = Clock.System.now(),
        ),
        toggleFavoured = {},
    )
}
