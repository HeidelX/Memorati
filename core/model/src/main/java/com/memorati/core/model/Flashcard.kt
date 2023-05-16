package com.memorati.core.model

import kotlinx.datetime.Instant

data class Flashcard(
    val id: Long,
    val front: String,
    val back: String,
    val createdAt: Instant,
    val favoured: Boolean = false,
)
