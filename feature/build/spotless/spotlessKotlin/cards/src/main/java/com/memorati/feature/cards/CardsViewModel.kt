package com.memorati.feature.cards

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    flashcardsRepository: FlashcardsRepository,
) : ViewModel() {
    val cards = flashcardsRepository.flashcards().map {
        listOf(
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),

        )
    }
}
