package com.memorati.feature.assistant

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.AssistantCard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.AssistantCardProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun AnswerRadioButtons(
    modifier: Modifier = Modifier,
    card: AssistantCard,
    onAnswerSelected: (AssistantCard, String) -> Unit,
) {
    Column(modifier = modifier) {
        card.answers.forEachIndexed { index, answer ->
            AnswerRadioButton(
                modifier = Modifier.padding(vertical = 0.5.dp),
                card = card,
                answer = answer,
                onAnswerSelected = onAnswerSelected,
            )
        }
    }
}

@Composable
private fun AnswerRadioButton(
    modifier: Modifier = Modifier,
    card: AssistantCard,
    answer: String,
    onAnswerSelected: (AssistantCard, String) -> Unit,
) {
    val selected = answer == card.answer
    Surface(
        color = surfaceColor(card, answer),
        modifier = modifier
            .selectable(
                enabled = !card.isAnswered,
                selected = selected,
                onClick = {
                    onAnswerSelected(card, answer)
                },
                role = Role.RadioButton,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(answer, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            RadioButton(selected, onClick = null)
        }
    }
}

@Composable
private fun surfaceColor(card: AssistantCard, answer: String): Color {
    val selected = card.answer == answer
    return if (card.isAnswered) {
        if (card.flashcard.back == answer) {
            Color.Green
        } else if (selected && !card.isCorrect) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.surface
        }
    } else {
        MaterialTheme.colorScheme.surface
    }.copy(alpha = 0.4f)
}

@Composable
@DevicePreviews
private fun AnswerRadioButtonsPreview(@PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard) {
    MemoratiTheme {
        Surface {
            AnswerRadioButtons(
                card = assistantCard,
                onAnswerSelected = { _, _ -> },
            )
        }
    }
}
