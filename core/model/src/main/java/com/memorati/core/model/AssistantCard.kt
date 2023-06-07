package com.memorati.core.model

data class AssistantCard(
    val flashcard: Flashcard,
    val answers: List<String>,
    val response: String? = null,
) {
    val isCorrect get() = response == flashcard.back
    val isAnswered get() = response != null
}
