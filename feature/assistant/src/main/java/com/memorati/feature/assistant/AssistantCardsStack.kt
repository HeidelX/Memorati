package com.memorati.feature.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.CardsStack
import com.memorati.core.model.DueCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.DueCardsProvider
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.assistant.state.AssistantCards

@Composable
internal fun AssistantCardsStack(
    modifier: Modifier = Modifier,
    state: AssistantCards,
    onUpdateCard: (DueCard) -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onAnswerSelected: (DueCard, String) -> Unit,
    onFlip: (DueCard, Boolean) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.shapes.small,
                )
                .padding(16.dp)
                .align(Alignment.TopCenter),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(R.string.due_flashcards, state.dueCardsCount),
        )

        CardsStack(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            items = state.dueCards,
            onSwipeCardEnd = { card ->
                if (card.isAnswered) onUpdateCard(card)
                card.isAnswered
            },
            onSwipeCardStart = { card ->
                if (card.isAnswered) onUpdateCard(card)
                card.isAnswered
            },
            itemKey = { it },
        ) { dueCard ->
            AssistantCard(
                modifier = Modifier.fillMaxWidth(),
                card = dueCard,
                initialFlipped = dueCard.flipped,
                toggleFavoured = toggleFavoured,
                onAnswerSelected = onAnswerSelected,
                onFlip = { onFlip(dueCard, it) },
            )
        }
    }
}

@Composable
@DevicePreviews
private fun AssistantCardsStackPreview(
    @PreviewParameter(DueCardsProvider::class) dueCards: List<DueCard>,
) {
    MemoratiTheme {
        Surface {
            AssistantCardsStack(
                state = AssistantCards(
                    dueCards = dueCards,
                    dueCardsCount = 20,
                ),
                toggleFavoured = { _, _ -> },
                onUpdateCard = { _ -> },
                onAnswerSelected = { _, _ -> },
                onFlip = { _, _ -> },
            )
        }
    }
}
