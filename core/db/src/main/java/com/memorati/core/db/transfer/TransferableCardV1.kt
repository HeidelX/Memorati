package com.memorati.core.db.transfer

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferableCardV1(
    @SerialName("idiom")
    val idiom: String,
    @SerialName("description")
    val description: String,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class DataTransferV1(
    @SerialName("version")
    @EncodeDefault
    val version: String = "1",

    @SerialName("cards")
    val cards: List<TransferableCardV1>,

    @SerialName("exported_at_utc")
    val exportedAt: Instant = Clock.System.now(),
)
