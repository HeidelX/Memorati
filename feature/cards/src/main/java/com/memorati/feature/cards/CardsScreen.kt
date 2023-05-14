package com.memorati.feature.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    CardsScreen(cards = cards)
}

@Composable
internal fun CardsScreen(
    cards: List<Flashcard>,
    modifier: Modifier = Modifier,
) {
    LazyColumn {
        items(cards) { card ->
            CardItem(card)
            Divider(thickness = 2.dp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CardItem(
    card: Flashcard,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = { },
    ) {
        Text(text = card.front)
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
