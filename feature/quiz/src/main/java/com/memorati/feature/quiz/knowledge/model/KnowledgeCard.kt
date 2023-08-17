package com.memorati.feature.quiz.knowledge.model

import com.memorati.core.model.Flashcard

data class KnowledgeCard(
    val flashcard: Flashcard,
    val flipped: Boolean,
)
