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
    onAddCard: () -> Unit,
    onEdit: (Flashcard) -> Unit,
    openSettings: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CardsScreen(
        modifier = modifier,
        state = state,
        onEdit = onEdit,
        onAddCard = onAddCard,
        openSettings = openSettings,
        speak = viewModel::speak,
        onDelete = viewModel::deleteCard,
        onQueryChange = viewModel::onQueryChange,
        toggleFavoured = viewModel::toggleFavoured,
    )
}
