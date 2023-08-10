package com.memorati.feature.quiz.knowledge

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FlippableCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun KnowledgeDirectionsCard(
    modifier: Modifier = Modifier,
    card: Flashcard,
) {
    FlippableCard(
        modifier = modifier.height(230.dp),
        front = {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                text = card.idiom,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        back = {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                text = card.meaning,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
    )
}

@DevicePreviews
@Composable
internal fun QuizCardPreview(
    @PreviewParameter(FlashcardProvider::class) flashcard: Flashcard,
) {
    MemoratiTheme {
        KnowledgeDirectionsCard(
            card = flashcard,
        )
    }
}
