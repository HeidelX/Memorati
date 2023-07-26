package com.memorati.core.testing.repository

import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Instant

class FakeFlashcardsRepository : FlashcardsRepository {
    override fun flashcards(): Flow<List<Flashcard>> {
        return flowOf(listOf())
    }

    override fun dueFlashcards(time: Instant): Flow<List<Flashcard>> {
        return flowOf(listOf())
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
}