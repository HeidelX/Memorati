package com.memorati.core.data.repository

import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.model.Flashcard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFlashcardsRepository @Inject constructor(
    private val flashcardsDao: FlashcardsDao,
) : FlashcardsRepository {
    override fun flashcards(): Flow<List<Flashcard>> {
        return flashcardsDao.getAll()
            .map { entities ->
                entities.map { entity ->
                    Flashcard(
                        front = entity.front,
                        back = entity.back,
                        createdAt = entity.createdAt,
                    )
                }
            }
    }

    override suspend fun createCard(flashcard: Flashcard) {
        flashcardsDao.insert(
            FlashcardEntity(
                id = 0,
                front = flashcard.front,
                back = flashcard.back,
                createdAt = flashcard.createdAt,
            ),
        )
    }
}
