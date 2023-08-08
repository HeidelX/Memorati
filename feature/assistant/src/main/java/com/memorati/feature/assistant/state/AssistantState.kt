package com.memorati.feature.assistant.state

import com.memorati.core.model.DueCard

sealed interface AssistantState

data class AssistantCards(
    val dueCards: List<DueCard> = emptyList(),
    val dueCardsCount: Int = 0,
) : AssistantState

object EmptyState : AssistantState
data class ReviewResult(
    val correctAnswers: Int,
    val wrongAnswers: Int,
) : AssistantState {
    val progress get() = correctAnswers.toFloat() / correctAnswers.plus(wrongAnswers)
}
