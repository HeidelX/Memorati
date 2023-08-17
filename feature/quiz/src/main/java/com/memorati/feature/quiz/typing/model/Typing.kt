package com.memorati.feature.quiz.typing.model

import com.memorati.core.model.Flashcard

data class Typing(
    val card: Flashcard,
    val percentage: Float = 1f,
    val idiom: String = "",
    val state: State = State.DEFAULT,
    val swipeState: SwipeState = SwipeState.DEFAULT,
) {
    enum class State {
        DEFAULT,
        CORRECT,
        WRONG,
    }

    enum class SwipeState {
        SWIPE,
        DEFAULT,
    }
}
