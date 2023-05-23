package com.memorati.feature.cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.model.Flashcard

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

@Composable
internal fun CardsScreen(
    state: CardsState,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
) {
    if (state.map.isNotEmpty()) {
        CardsList(state, toggleFavoured, modifier)
    } else {
        EmptyCardsScreen()
    }
}
