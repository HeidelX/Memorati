package com.memorati.core.data.repository

import com.memorati.core.data.mapper.toFlashcard
import com.memorati.core.data.mapper.toFlashcardEntity
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.model.Flashcard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun favourites(): Flow<List<Flashcard>> =
        flashcardsDao.getFavourites().map { entities ->
            entities.map { entity ->
                entity.toFlashcard()
            }
        }

    override suspend fun createCard(flashcard: Flashcard) {
        flashcardsDao.insert(
            flashcard.toFlashcardEntity(),
        )
    }

    override suspend fun updateCard(flashcard: Flashcard) {
        flashcardsDao.update(flashcard.toFlashcardEntity())
    }
}
