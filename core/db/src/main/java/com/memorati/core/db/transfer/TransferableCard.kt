package com.memorati.core.db.transfer

import kotlinx.datetime.Instant

sealed interface TransferableCard {
    val idiom: String
    val meaning: String
    val idiomLanguageTag: String?
}

sealed interface DataTransfer {
    val version: String
    val cards: List<TransferableCard>
    val exportedAt: Instant
}
