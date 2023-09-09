package com.memorati.feature.quiz.typing.model

import com.memorati.core.model.Flashcard

data class Typing(
    val card: Flashcard,
    val swipeState: SwipeState = SwipeState.DEFAULT,
) {
    enum class SwipeState {
        SWIPE,
        DEFAULT,
    }
}
