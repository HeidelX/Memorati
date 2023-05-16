package com.memorati.core.data.mapper

import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.model.Flashcard

fun FlashcardEntity.toFlashcard() = Flashcard(
    id = id,
    front = front,
    back = back,
    createdAt = createdAt,
    favoured = favoured,
)

fun Flashcard.toFlashcardEntity() = FlashcardEntity(
    id = id,
    front = front,
    back = back,
    createdAt = createdAt,
    favoured = favoured,
)
