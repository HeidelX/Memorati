package com.memorati.core.db.transfer

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferableCardV2(
    @SerialName("idiom")
    val idiom: String,
    @SerialName("meaning")
    val meaning: String,
    @SerialName("idiomLanguageTag")
    val idiomLanguageTag: String?,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class DataTransferV2(
    @SerialName("version")
    @EncodeDefault
    val version: String = "2",

    @SerialName("cards")
    val cards: List<TransferableCardV2>,

    @SerialName("exported_at_utc")
    val exportedAt: Instant = Clock.System.now(),
)
