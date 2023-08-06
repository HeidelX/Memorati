package com.memorati.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardsProvider

@Composable
internal fun QuizScreen(
    modifier: Modifier = Modifier,
    flashcards: List<Flashcard>,
    onSwipeCardRight: (Flashcard) -> Unit,
    onSwipeCardLeft: (Flashcard) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        QuizStack(
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
