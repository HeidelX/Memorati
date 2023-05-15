package com.memorati.feature.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
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
    CardsScreen(cards, modifier)
}

@Composable
internal fun CardsScreen(
    cards: List<Flashcard>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),

    ) {
        items(cards) { card ->
            CardItem(card)
            Spacer(modifier = modifier.height(10.dp))
        }
    }
}

@Composable
internal fun CardItem(
    card: Flashcard,
    modifier: Modifier = Modifier,
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

                    Text(
                        modifier = modifier.padding(top = 20.dp),
                        text = card.back,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                Icon(
                    Icons.Rounded.FavoriteBorder,
                    contentDescription = "",
                    modifier = modifier.align(Alignment.BottomEnd),
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
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
        ),
    )
}

@DevicePreviews
@Composable
internal fun CardItemPreview() {
    CardItem(
        card = Flashcard(
            front = "Hello",
            back = "Hallo",
            createdAt = Clock.System.now(),
        ),
    )
}
