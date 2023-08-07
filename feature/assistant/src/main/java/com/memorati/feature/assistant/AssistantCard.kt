package com.memorati.feature.assistant

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FavouriteButton
import com.memorati.core.design.component.FlippableCard
import com.memorati.core.model.DueCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.DueCardProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun AssistantCard(
    modifier: Modifier = Modifier,
    card: DueCard,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onAnswerSelected: (DueCard, String) -> Unit,
) {
    FlippableCard(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        front = {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = card.flashcard.idiom,
                style = MaterialTheme.typography.titleLarge,
            )

            FavouriteButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                favoured = card.flashcard.favoured,
                onCheckedChange = {
                    toggleFavoured(card.flashcard, it)
                },
            )
        },
        back = {
            AnswerRadioButtons(
                modifier = Modifier.align(Alignment.Center),
                card = card,
                onAnswerSelected = onAnswerSelected,
            )

            FavouriteButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                favoured = card.flashcard.favoured,
                onCheckedChange = {
                    toggleFavoured(card.flashcard, it)
                },
            )
        },
    )
}

@Composable
@DevicePreviews
private fun AssistantPagePreview(
    @PreviewParameter(DueCardProvider::class) dueCard: DueCard,
) {
    MemoratiTheme {
        AssistantCard(
            card = dueCard.copy(answer = "Communication skills"),
            toggleFavoured = { _, _ -> },
            onAnswerSelected = { _, _ -> },
        )
    }
}
