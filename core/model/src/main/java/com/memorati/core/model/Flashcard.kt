package com.memorati.core.model

import kotlinx.datetime.Instant

data class Flashcard(
    val id: Long,
    val idiom: String,
    val meaning: String,
    val createdAt: Instant,
    val lastReviewAt: Instant,
    val nextReviewAt: Instant,
    val topics: List<Topic> = emptyList(),
    val favoured: Boolean = false,
    val additionalInfo: AdditionalInfo = AdditionalInfo(),
    val idiomLanguageTag: String?,
)

data class AdditionalInfo(
    val difficulty: Double = 1.0,
    val consecutiveCorrectCount: Int = 0,
    val memoryStrength: Double = 1.0,
)
