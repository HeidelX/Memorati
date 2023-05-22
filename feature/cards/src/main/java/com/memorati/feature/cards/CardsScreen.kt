package com.memorati.feature.cards

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

@Composable
internal fun CardsRoute(
    modifier: Modifier = Modifier,
    viewModel: CardsViewModel = hiltViewModel(),
) {
    val state by viewModel.cards.collectAsStateWithLifecycle()
    CardsScreen(state, modifier) { card ->
        viewModel.toggleFavoured(card)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CardsScreen(
    state: CardsState,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
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
            items(cards) { card ->
                CardItem(card, toggleFavoured = toggleFavoured)
                Spacer(modifier = modifier.height(10.dp))
            }
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

                FilledIconToggleButton(
                    modifier = modifier.align(Alignment.BottomEnd),
                    checked = card.favoured,
                    onCheckedChange = {
                        toggleFavoured(card)
                    },
                    colors = IconButtonDefaults.iconToggleButtonColors(
                        checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = if (card.favoured) {
                            MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.12f,
                            )
                        } else {
                            Color.Transparent
                        },
                    ),
                ) {
                    Icon(
                        imageVector = if (card.favoured) {
                            MemoratiIcons.Favourites
                        } else {
                            MemoratiIcons.FavouritesBorder
                        },
                        contentDescription = "",
                    )
                }
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
    ) {}
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
