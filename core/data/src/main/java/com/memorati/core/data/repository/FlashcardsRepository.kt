package com.memorati.core.data.repository

import com.memorati.core.model.Flashcard
import kotlinx.coroutines.flow.Flow

interface FlashcardsRepository {
    fun flashcards(): Flow<List<Flashcard>>
    fun favourites(): Flow<List<Flashcard>>
    fun findById(id: Long): Flow<Flashcard?>

    suspend fun createCard(flashcard: Flashcard)
    suspend fun updateCard(flashcard: Flashcard)
    suspend fun deleteCard(flashcard: Flashcard)
}
