package com.memorati.feature.favourites

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.Flashcard

@Composable
internal fun FavouritesRoute(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel = hiltViewModel(),
) {
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    FavouritesScreen(cards, modifier) { card ->
        viewModel.toggleFavoured(card)
    }
}

@Composable
internal fun FavouritesScreen(
    cards: List<Flashcard>,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
) {
    if (cards.isNotEmpty()) {
        FavouritesList(cards, toggleFavoured, modifier)
    } else {
        EmptyScreen(
            imageVector = MemoratiIcons.FavouritesBorder,
            message = stringResource(id = R.string.no_favourites_message),
        )
    }
}
