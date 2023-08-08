package com.memorati.feature.assistant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.DueCard
import com.memorati.core.model.Flashcard
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.AssistantState
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewResult

@Composable
fun AssistantRoute(
    modifier: Modifier = Modifier,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AssistantScreen(
        modifier = modifier,
        state = state,
        onAnswerSelected = viewModel::onAnswerSelected,
        onUpdateCard = viewModel::updateCard,
        toggleFavoured = viewModel::toggleFavoured,
        onFlip = viewModel::onFlip,
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    state: AssistantState,
    onAnswerSelected: (DueCard, String) -> Unit,
    onUpdateCard: (DueCard) -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onFlip: (DueCard, Boolean) -> Unit,
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1.0f)) {
            when (state) {
                is AssistantCards -> AssistantCardsStack(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onUpdateCard = onUpdateCard,
                    toggleFavoured = toggleFavoured,
                    onAnswerSelected = onAnswerSelected,
                    onFlip = onFlip,
                )

                is ReviewResult -> ReviewResultScreen(reviewResult = state)

                EmptyState -> EmptyScreen(
                    resource = R.raw.assistant,
                    message = stringResource(id = R.string.no_assistant_cards_message),
                )
            }
        }

        NotificationPermissionTile()
    }
}

@Composable
@Preview
private fun AssistantEmptyScreenPreview() {
    AssistantScreen(
        state = EmptyState,
        onAnswerSelected = { _, _ -> },
        onUpdateCard = { _ -> },
        toggleFavoured = { _, _ -> },
        onFlip = { _, _ -> },
    )
}
