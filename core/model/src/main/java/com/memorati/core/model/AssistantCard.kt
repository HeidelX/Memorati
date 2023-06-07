package com.memorati.core.model

data class AssistantCard(
    val flashcard: Flashcard,
    val answers: List<String>,
    val response: Answer = Answer.NO_ANSWER,
) {
    val isCorrect get() = response == Answer.CORRECT
    val isAnswered get() = response != Answer.NO_ANSWER

    enum class Answer {
        CORRECT,
        WRONG,
        NO_ANSWER,
    }
}