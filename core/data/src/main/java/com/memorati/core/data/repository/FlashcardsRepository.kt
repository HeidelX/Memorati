package com.memorati.core.data.repository

import com.memorati.core.model.Flashcard
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface FlashcardsRepository {
    fun flashcards(): Flow<List<Flashcard>>
    fun dueFlashcards(time: Instant = Clock.System.now()): Flow<List<Flashcard>>
    fun favourites(): Flow<List<Flashcard>>
    fun findById(id: Long): Flow<Flashcard?>

    suspend fun searchBy(query: String): List<Flashcard>
    suspend fun createCard(flashcard: Flashcard)
    suspend fun updateCard(flashcard: Flashcard)
    suspend fun updateCards(flashcards: List<Flashcard>)
    suspend fun deleteCard(flashcard: Flashcard)
    suspend fun clear()
}
