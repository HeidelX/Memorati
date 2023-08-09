package com.memorati.feature.assistant.state

import com.memorati.core.model.DueCard

sealed interface AssistantState

data class AssistantCards(
    val dueCards: List<DueCard> = emptyList(),
    val dueCardsCount: Int = 0,
) : AssistantState

data object EmptyState : AssistantState
data class ReviewCardsStats(
    val totalCount: Int,
    val reiteratedAccuracy: Int,
    val soloAccuracy: Int,
    val zeroAccuracy: Int,
) : AssistantState {
    val reiteratedAccuracyProgress get() = reiteratedAccuracy.toFloat() / totalCount
    val zeroAccuracyProgress get() = zeroAccuracy.toFloat() / totalCount
    val soloAccuracyProgress get() = soloAccuracy.toFloat() / totalCount
}
