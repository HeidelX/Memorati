package com.memorati.core.db.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalInfoEntity(
    @SerialName("difficulty")
    val difficulty: Double = 1.0,
    @SerialName("consecutiveCorrectCount")
    val consecutiveCorrectCount: Int = 0,
    @SerialName("memoryStrength")
    val memoryStrength: Double = 1.0,
)