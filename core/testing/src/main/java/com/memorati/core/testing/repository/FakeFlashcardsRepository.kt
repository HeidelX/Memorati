package com.memorati.core.testing.repository

import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AdditionalInfo
import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class FakeFlashcardsRepository : FlashcardsRepository {

    override fun flashcards(): Flow<List<Flashcard>> {
        return flowOf(listOf())
    }

    override fun dueFlashcards(time: Instant): Flow<List<Flashcard>> {
        return flowOf(generate())
    }

    override fun favourites(): Flow<List<Flashcard>> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Flow<Flashcard?> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBy(query: String): List<Flashcard> {
        TODO("Not yet implemented")
    }

    override suspend fun createCard(flashcard: Flashcard) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCard(flashcard: Flashcard) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCards(flashcards: List<Flashcard>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCard(flashcard: Flashcard) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

    private fun generate(): MutableList<Flashcard> {
        val flashcards = mutableListOf<Flashcard>()

        val initialCreatedAt = Clock.System.now().toEpochMilliseconds()
        var createdAt = initialCreatedAt
        var lastReviewAt = createdAt + 1000
        var nextReviewAt = lastReviewAt + 1000

        for (i in 1L..100L) {
            flashcards.add(
                Flashcard(
                    id = i,
                    idiom = "Idiom $i",
                    meaning = "Meaning $i",
                    createdAt = Instant.fromEpochMilliseconds(createdAt),
                    lastReviewAt = Instant.fromEpochMilliseconds(lastReviewAt),
                    nextReviewAt = Instant.fromEpochMilliseconds(nextReviewAt),
                    favoured = false,
                    additionalInfo = AdditionalInfo(
                        difficulty = (Math.random() * 0.8 + 0.2),
                        consecutiveCorrectCount = (Math.random() * 10).toInt(),
                        memoryStrength = 1.0,
                    ),
                    idiomLanguageTag = "de",
                    topics = listOf(
                        Topic(id = 1, label = "DE"),
                        Topic(id = 1, label = "A2"),
                    ),
                ),
            )

            if (i % 30L == 0L) {
                createdAt = initialCreatedAt + (i / 30) * 24 * 60 * 60 * 1000L
                lastReviewAt = createdAt + 1000
                nextReviewAt = lastReviewAt + 1000
            } else {
                createdAt += 1000
                lastReviewAt += 1000
                nextReviewAt += 1000
            }
        }

        return flashcards
    }
}
