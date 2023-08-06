package com.memorati.feature.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardsProvider

@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    flashcards: List<Flashcard>,
    onSwipeCardLeft: () -> Unit,
    onSwipeCardRight: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        flashcards.forEachIndexed { order, card ->
            key(card) {
                SwipeableCard(
                    order = order,
                    count = flashcards.size,
                    onSwipe = { offsetX ->
                        if (offsetX >= 500) onSwipeCardRight() else onSwipeCardLeft()
                    },
                ) {
                    QuizCard(
                        order = order,
                        flip = false,
                        card = card,
                    ) {
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun CardStackPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    QuizScreen(
        flashcards = flashcards,
        onSwipeCardLeft = {},
        onSwipeCardRight = {},
    )
}
