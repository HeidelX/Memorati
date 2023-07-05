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
        state = state,
        modifier = modifier,
        toggleFavoured = viewModel::toggleFavoured,
        onDelete = viewModel::deleteCard,
        onEdit = onEdit,
        onAddCard = onAddCard,
        onQueryChange = viewModel::onQueryChange,
        openSettings = openSettings,
        speak = viewModel::speak,
    )
}
