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
    override val idiom: String,
    @SerialName("meaning")
    override val meaning: String,
    @SerialName("idiomLanguageTag")
    override val idiomLanguageTag: String?,
) : TransferableCard

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class DataTransferV2(
    @SerialName("version")
    @EncodeDefault
    override val version: String = "2",

    @SerialName("cards")
    override val cards: List<TransferableCardV2>,

    @SerialName("exported_at_utc")
    override val exportedAt: Instant = Clock.System.now(),
) : DataTransfer
