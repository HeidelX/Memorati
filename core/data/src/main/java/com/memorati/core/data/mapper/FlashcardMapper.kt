package com.memorati.core.data.mapper

import com.memorati.core.db.model.AdditionalInfoEntity
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.model.FlashcardsWithTopics
import com.memorati.core.model.AdditionalInfo
import com.memorati.core.model.Flashcard

fun FlashcardEntity.toFlashcard() = Flashcard(
    id = flashcardId,
    idiom = idiom,
    meaning = meaning,
    createdAt = createdAt,
    favoured = favoured,
    lastReviewAt = lastReviewAt,
    nextReviewAt = nextReviewAt,
    additionalInfo = additionalInfoEntity.toAdditionalInfo(),
    idiomLanguageTag = idiomLanguageTag,
)

fun FlashcardsWithTopics.toFlashcard() = with(flashcard) {
    Flashcard(
        id = flashcardId,
        idiom = idiom,
        meaning = meaning,
        createdAt = createdAt,
        favoured = favoured,
        lastReviewAt = lastReviewAt,
        nextReviewAt = nextReviewAt,
        topics = topics.map { topicEntity -> topicEntity.toTopic() },
        idiomLanguageTag = idiomLanguageTag,
        additionalInfo = additionalInfoEntity.toAdditionalInfo(),
    )
}

fun Flashcard.toFlashcardEntity() = FlashcardEntity(
    flashcardId = id,
    idiom = idiom,
    meaning = meaning,
    createdAt = createdAt,
    favoured = favoured,
    lastReviewAt = lastReviewAt,
    nextReviewAt = nextReviewAt,
    additionalInfoEntity = additionalInfo.toAdditionalInfoEntity(),
    idiomLanguageTag = idiomLanguageTag,
)

private fun AdditionalInfoEntity.toAdditionalInfo() = AdditionalInfo(
    difficulty = difficulty,
    consecutiveCorrectCount = consecutiveCorrectCount,
    memoryStrength = memoryStrength,
)

private fun AdditionalInfo.toAdditionalInfoEntity() = AdditionalInfoEntity(
    difficulty = difficulty,
    consecutiveCorrectCount = consecutiveCorrectCount,
    memoryStrength = memoryStrength,
)
