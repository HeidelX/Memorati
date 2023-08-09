package com.memorati.core.model

data class DueCard(
    val flashcard: Flashcard,
    val answers: List<String>,
    val answer: String? = null,
    val flipped: Boolean = false,
) {
    val isCorrect get() = answer == flashcard.meaning
    val isAnswered get() = answer != null
}
