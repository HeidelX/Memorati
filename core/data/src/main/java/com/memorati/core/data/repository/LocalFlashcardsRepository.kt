package com.memorati.core.data.repository

import com.memorati.core.data.mapper.toFlashcard
import com.memorati.core.data.mapper.toFlashcardEntity
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.model.Flashcard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFlashcardsRepository @Inject constructor(
    private val flashcardsDao: FlashcardsDao,
) : FlashcardsRepository {

    override fun flashcards(): Flow<List<Flashcard>> =
        flashcardsDao.allFlashcardWithTopics()
            .map { entities ->
                entities.map { entity ->
                    entity.toFlashcard()
                }
            }

    override fun flashcardsToReview(time: Instant): Flow<List<Flashcard>> =
        flashcardsDao.flashcardToReview(time.toEpochMilliseconds())
            .map { entities ->
                val today = time.toLocalDateTime(TimeZone.currentSystemDefault()).date
                entities.map { it.toFlashcard() }
                    .filter {
                        it.lastReviewAt.toLocalDateTime(TimeZone.currentSystemDefault()).date != today
                    }
                    .filter { it.additionalInfo.consecutiveCorrectCount < 2 }
                    .shuffled()
                    .take(30)
            }

    override fun favourites(): Flow<List<Flashcard>> =
        flashcardsDao.getFavourites().map { entities ->
            entities.map { entity ->
                entity.toFlashcard()
            }
        }

    override fun findById(id: Long): Flow<Flashcard?> =
        flashcardsDao.find(id).map { entity ->
            entity?.toFlashcard()
        }

    override suspend fun createCard(flashcard: Flashcard) {
        flashcardsDao.insert(
            flashcard.toFlashcardEntity(),
        )
    }

    override suspend fun updateCard(flashcard: Flashcard) {
        flashcardsDao.update(flashcard.toFlashcardEntity())
    }

    override suspend fun updateCards(flashcards: List<Flashcard>) {
        flashcardsDao.update(flashcards.map { it.toFlashcardEntity() })
    }

    override suspend fun deleteCard(flashcard: Flashcard) {
        flashcardsDao.delete(flashcard.toFlashcardEntity())
    }

    override suspend fun searchBy(query: String): List<Flashcard> {
        return flashcardsDao.findBy(query).map {
            it.toFlashcard()
        }
    }

    override suspend fun clear() {
        flashcardsDao.clear()
    }
}
