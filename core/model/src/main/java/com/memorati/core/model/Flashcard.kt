package com.memorati.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Flashcard(
    val id: Long,
    val front: String,
    val back: String,
    val createdAt: Instant,
    val topics: List<Topic> = emptyList(),
    val favoured: Boolean = false,
    val additionalInfo: AdditionalInfo = AdditionalInfo()
)

data class AdditionalInfo(
    val difficulty: Double = 1.0,
    val consecutiveCorrectCount: Int = 0,
    val lastReviewTime: Instant = Clock.System.now(),
    val nextReviewTime: Instant = Clock.System.now(),
    val memoryStrength: Double = 1.0
)
