package com.memorati.feature.assistant.model

import com.memorati.core.model.Flashcard

data class AssistantCard(
    val flashcard: Flashcard,
    val answers: List<String>,
)
