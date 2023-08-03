package com.memorati.core.testing.repository

import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AdditionalInfo
import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.random.Random

class TestFlashcardsRepository : FlashcardsRepository {

    private val cardsMap = generate().associateBy { it.id }.toMutableMap()
    private val state = MutableStateFlow(cardsMap.values.toList())

    override fun flashcards(): Flow<List<Flashcard>> {
        return state
    }

    override fun dueFlashcards(time: Instant): Flow<List<Flashcard>> {
        return state.map { cards ->
            cards.filter { it.nextReviewAt <= time }.toList()
        }
    }

    override fun favourites(): Flow<List<Flashcard>> {
        return state.map { cards ->
            cards.filter { it.favoured }.toList()
        }
    }

    override fun findById(id: Long): Flow<Flashcard?> {
        return flowOf(cardsMap[id])
    }

    override suspend fun searchBy(query: String): List<Flashcard> {
        return cardsMap.values.filter {
            it.idiom.contains(query, true) || it.meaning.contains(query, true)
        }
    }

    override suspend fun createCard(flashcard: Flashcard) {
        state.update {
            cardsMap[flashcard.id] = flashcard
            cardsMap.values.toList()
        }
    }

    override suspend fun updateCard(flashcard: Flashcard) {
        state.update {
            cardsMap[flashcard.id] = flashcard
            cardsMap.values.toList()
        }
    }

    override suspend fun updateCards(flashcards: List<Flashcard>) {
        flashcards.forEach { updateCard(it) }
    }

    override suspend fun deleteCard(flashcard: Flashcard) {
        state.update {
            cardsMap.remove(flashcard.id)
            cardsMap.values.toList()
        }
    }

    override suspend fun clear() {
        state.update {
            cardsMap.clear()
            cardsMap.values.toList()
        }
    }

    private fun generate(): MutableList<Flashcard> {
        val flashcards = mutableListOf<Flashcard>()

        val initialCreatedAt = Clock.System.now().toEpochMilliseconds()
        var createdAt = initialCreatedAt
        var lastReviewAt = createdAt + 1000
        var nextReviewAt = lastReviewAt + 1000

        for (i in 1L..300L) {
            flashcards.add(
                Flashcard(
                    id = i,
                    idiom = "Idiom $i",
                    meaning = "Meaning -$i",
                    createdAt = Instant.fromEpochMilliseconds(createdAt),
                    lastReviewAt = Instant.fromEpochMilliseconds(lastReviewAt),
                    nextReviewAt = Instant.fromEpochMilliseconds(nextReviewAt),
                    favoured = false,
                    additionalInfo = AdditionalInfo(
                        difficulty = Random.nextDouble(1.0),
                        consecutiveCorrectCount = Random(10).nextInt(),
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
