package com.memorati.feature.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardsProvider

@Composable
internal fun QuizScreen(
    modifier: Modifier = Modifier,
    flashcards: List<Flashcard>,
    onSwipeCardRight: () -> Unit,
    onSwipeCardLeft: () -> Unit,
) {
    Column(modifier = modifier) {
        CardStack(
            flashcards = flashcards,
            onSwipeCardLeft = onSwipeCardLeft,
            onSwipeCardRight = onSwipeCardRight,
        )
    }
}

@DevicePreviews
@Composable
private fun QuizScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    QuizScreen(
        flashcards = flashcards,
        onSwipeCardLeft = {},
        onSwipeCardRight = {},
    )
}
