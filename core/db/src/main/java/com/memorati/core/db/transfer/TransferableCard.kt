package com.memorati.core.db.transfer

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed interface TransferableCard {
    val idiom: String
    val meaning: String
    val idiomLanguageTag: String?
}

@Serializable
sealed interface DataTransfer {
    val version: String
    val cards: List<TransferableCard>
    val exportedAt: Instant
}
