package com.memorati.core.model

import kotlinx.datetime.Instant

data class Flashcard(
    val front: String,
    val back: String,
    val createdAt: Instant,
)
