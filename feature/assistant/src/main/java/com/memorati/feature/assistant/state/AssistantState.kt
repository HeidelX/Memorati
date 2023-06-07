package com.memorati.feature.assistant.state

import com.memorati.core.model.AssistantCard

data class AssistantState(
    val reviews: List<AssistantCard> = emptyList(),
)
