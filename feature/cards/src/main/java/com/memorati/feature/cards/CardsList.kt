package com.memorati.feature.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun CardsList(
    state: CardsState,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit = {},
    onDelete: (Flashcard) -> Unit = {},
    onEdit: (Flashcard) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),
    ) {
        state.map.forEach { (date, cards) ->
            stickyHeader {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(all = 8.dp),
                    text = date.toString(),
                )
            }
            items(cards, key = { it.id }) { card ->
                CardItem(
                    card = card,
                    toggleFavoured = toggleFavoured,
                    onDelete = onDelete,
                    onEdit = onEdit,
                )
                Spacer(modifier = modifier.height(10.dp))
            }
        }
    }
}

@DevicePreviews
@Composable
internal fun CardsScreenPreview() {
    CardsScreen(
        state = CardsState(
            mapOf(
                LocalDate(2023, 12, 10) to listOf(
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
            ),
        ),
        toggleFavoured = {},
        onEdit = {},
        onDelete = {},
    )
}