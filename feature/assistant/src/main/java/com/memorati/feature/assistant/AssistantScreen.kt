package com.memorati.feature.assistant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.CardsStack
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.DueCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.DueCardsProvider
import com.memorati.core.ui.theme.MemoratiTheme
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
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    state: AssistantState,
    onAnswerSelected: (DueCard, String) -> Unit,
    onUpdateCard: (DueCard) -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1.0f)) {
            when (state) {
                is AssistantCards -> CardsStack(
                    modifier = Modifier.align(Alignment.Center),
                    items = state.dueCards,
                    onSwipeCardEnd = { card -> onUpdateCard(card) },
                    onSwipeCardStart = { card -> onUpdateCard(card) },
                    itemKey = { card -> card.flashcard.id }
                ) { assistantCard ->
                    AssistantCard(
                        modifier = Modifier.fillMaxWidth(),
                        card = assistantCard,
                        toggleFavoured = toggleFavoured,
                        onAnswerSelected = onAnswerSelected,
                    )
                }

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
@DevicePreviews
private fun AssistantScreenPreview(
    @PreviewParameter(DueCardsProvider::class) dueCards: List<DueCard>,
) {
    MemoratiTheme {
        AssistantScreen(
            state = AssistantCards(dueCards),
            toggleFavoured = { _, _ -> },
            onUpdateCard = { _ -> },
            onAnswerSelected = { _, _ -> },
        )
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
    )
}
