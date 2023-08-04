package com.memorati.core.db.transfer

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class TransferableCardV1(
    @SerialName("idiom")
    override val idiom: String,
    @SerialName("description")
    val description: String,
) : TransferableCard {

    @Transient
    override val meaning: String = description

    @Transient
    override val idiomLanguageTag: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class DataTransferV1(
    @SerialName("version")
    @EncodeDefault
    override val version: String = "1",

    @SerialName("cards")
    override val cards: List<TransferableCardV1>,

    @SerialName("exported_at_utc")
    override val exportedAt: Instant = Clock.System.now(),
) : DataTransfer
