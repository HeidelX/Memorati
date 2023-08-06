package com.memorati.feature.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.memorati.core.model.Flashcard

@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    flashcards: List<Flashcard>,
    onSwipeCardLeft: () -> Unit,
    onSwipeCardRight: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        flashcards.forEachIndexed { index, card ->
            key(card) {
                SwipeableCard(
                    order = index,
                    count = flashcards.size,
                    onSwipe = { offsetX ->
                        if (offsetX >= 500) onSwipeCardRight() else onSwipeCardLeft()
                    },
                ) {
                }
            }
        }
    }
}
