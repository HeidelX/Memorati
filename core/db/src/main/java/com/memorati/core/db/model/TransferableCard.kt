package com.memorati.core.db.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferableCard(
    @SerialName("idiom")
    val idiom: String,
    @SerialName("description")
    val description: String,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class DataTransfer(
    @SerialName("version")
    @EncodeDefault
    val version: String = "1",

    @SerialName("cards")
    val cards: List<TransferableCard>,

    @SerialName("exported_at_utc")
    val exportedAt: Instant = Clock.System.now(),
)
