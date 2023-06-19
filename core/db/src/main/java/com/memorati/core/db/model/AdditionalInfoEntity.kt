package com.memorati.core.db.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class AdditionalInfoEntity(
    @EncodeDefault
    @SerialName("difficulty")
    val difficulty: Double = 1.0,
    @EncodeDefault
    @SerialName("consecutiveCorrectCount")
    val consecutiveCorrectCount: Int = 0,
    @EncodeDefault
    @SerialName("memoryStrength")
    val memoryStrength: Double = 1.0,
)
