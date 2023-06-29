package com.memorati.feature.assistant.state

import com.memorati.core.model.AssistantCard

sealed interface AssistantState

data class AssistantCards(
    val reviews: List<AssistantCard> = emptyList(),
) : AssistantState

object EmptyState : AssistantState
data class ReviewResult(
    val correctAnswers: Int,
    val wrongAnswers: Int,
) : AssistantState {
    val progress get() = correctAnswers.toFloat() / correctAnswers.plus(wrongAnswers)
}
