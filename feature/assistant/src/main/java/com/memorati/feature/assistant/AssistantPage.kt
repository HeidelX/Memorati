package com.memorati.feature.assistant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FavouriteButton
import com.memorati.core.model.AssistantCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.AssistantCardProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun AssistantPage(
    modifier: Modifier = Modifier,
    card: AssistantCard,
    onNext: () -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onAnswerSelected: (AssistantCard, String) -> Unit,
) {
    Box(
        modifier = modifier.padding(
            vertical = 32.dp,
            horizontal = 16.dp,
        ),
    ) {
        FlippableCard(
            front = {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = card.flashcard.idiom,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            back = {
                AnswerRadioButtons(
                    modifier = Modifier.align(Alignment.Center),
                    card = card,
                    onAnswerSelected = onAnswerSelected,
                )
            },
        )

        FavouriteButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart),
            favoured = card.favoured,
            onCheckedChange = {
                toggleFavoured(card.flashcard, it)
            },
        )

        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            enabled = card.isAnswered,
            onClick = onNext,
        ) {
            Text(
                text = stringResource(R.string.next),
            )
        }
    }
}

@Composable
@DevicePreviews
private fun AssistantPagePreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    MemoratiTheme {
        AssistantPage(
            card = assistantCard.copy(answer = "Communication skills"),
            onNext = {},
            toggleFavoured = { _, _ -> },
            onAnswerSelected = { _, _ -> },
        )
    }
}