package com.memorati.core.model

data class AssistantCard(
    val flashcard: Flashcard,
    val answers: List<String>,
    val answer: String? = null,
    val favoured: Boolean = false,
) {
    val isCorrect get() = answer == flashcard.meaning
    val isAnswered get() = answer != null
}
