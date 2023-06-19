package com.memorati.core.data.mapper

import com.memorati.core.db.model.AdditionalInfoEntity
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.model.FlashcardsWithTopics
import com.memorati.core.model.AdditionalInfo
import com.memorati.core.model.Flashcard

fun FlashcardEntity.toFlashcard() = Flashcard(
    id = flashcardId,
    front = front,
    back = back,
    createdAt = createdAt,
    favoured = favoured,
    lastReviewAt = lastReviewAt,
    nextReviewAt = nextReviewAt,
    additionalInfo = AdditionalInfo(
        difficulty = additionalInfoEntity.difficulty,
        consecutiveCorrectCount = additionalInfoEntity.consecutiveCorrectCount,
        memoryStrength = additionalInfoEntity.memoryStrength,
    )
)

fun FlashcardsWithTopics.toFlashcard() = Flashcard(
    id = flashcard.flashcardId,
    front = flashcard.front,
    back = flashcard.back,
    createdAt = flashcard.createdAt,
    favoured = flashcard.favoured,
    lastReviewAt = flashcard.lastReviewAt,
    nextReviewAt = flashcard.nextReviewAt,
    topics = topics.map { topicEntity -> topicEntity.toTopic() },
)

fun Flashcard.toFlashcardEntity() = FlashcardEntity(
    flashcardId = id,
    front = front,
    back = back,
    createdAt = createdAt,
    favoured = favoured,
    lastReviewAt = lastReviewAt,
    nextReviewAt = nextReviewAt,
    additionalInfoEntity = AdditionalInfoEntity(
        difficulty = additionalInfo.difficulty,
        consecutiveCorrectCount = additionalInfo.consecutiveCorrectCount,
        memoryStrength = additionalInfo.memoryStrength,
    )
)
