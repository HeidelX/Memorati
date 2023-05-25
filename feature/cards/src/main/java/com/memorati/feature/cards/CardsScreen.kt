package com.memorati.feature.cards

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.design.icon.CardMembership
import com.memorati.core.model.Flashcard

@Composable
internal fun CardsRoute(
    modifier: Modifier = Modifier,
    viewModel: CardsViewModel = hiltViewModel(),
    onAddCard: () -> Unit,
    onEdit: (Flashcard) -> Unit,
) {
    val state by viewModel.cards.collectAsStateWithLifecycle()
    CardsScreen(
        state = state,
        modifier = modifier,
        toggleFavoured = viewModel::toggleFavoured,
        onDelete = viewModel::deleteCard,
        onEdit = onEdit,
        onAddCard = onAddCard,
    )
}

@Composable
internal fun CardsScreen(
    state: CardsState,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
    onDelete: (Flashcard) -> Unit,
    onEdit: (Flashcard) -> Unit,
    onAddCard: () -> Unit,
) {
    if (state.map.isNotEmpty()) {
        CardsList(
            modifier = modifier,
            state = state,
            toggleFavoured = toggleFavoured,
            onEdit = onEdit,
            onDelete = onDelete,
            onAddCard = onAddCard,
        )
    } else {
        EmptyScreen(
            imageVector = MemoratiIcons.CardMembership,
            message = stringResource(id = R.string.no_cards_message),
        )
    }
}
