package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.answer
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.domain.GetDueFlashcards
import com.memorati.core.model.AssistantCard
import com.memorati.core.model.Flashcard
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    getDueFlashcards: GetDueFlashcards,
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val userReviews = MutableStateFlow<Map<Long, String>>(emptyMap())
    private val favourites = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    private val showResult = MutableStateFlow(false)
    private val flashcards = getDueFlashcards().onEach {
        userReviews.update { emptyMap() }
        showResult.update { false }
        favourites.update { emptyMap() }
    }

    val state = combine(
        flashcards,
        userReviews,
        showResult,
        favourites,
    ) { cards, reviews, showResult, favourites ->

        val reviewedCards = cards.map { card ->
            card.copy(
                answer = reviews[card.flashcard.id],
                favoured = favourites[card.flashcard.id] ?: card.flashcard.favoured,
            )
        }

        when {
            cards.isEmpty() -> EmptyState
            showResult -> ReviewResult(
                correctAnswers = reviewedCards.count { it.isCorrect },
                wrongAnswers = reviewedCards.count { !it.isCorrect },
            )

            else -> AssistantCards(reviews = reviewedCards)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyState,
    )

    fun onAnswerSelected(card: AssistantCard, selection: String) =
        userReviews.update { values ->
            values.toMutableMap()
                .apply { this[card.flashcard.id] = selection }
                .toMap()
        }

    fun updateCard(card: AssistantCard, lastPage: Boolean) = viewModelScope.launch {
        val flashcard = card.flashcard.answer(card.isCorrect)
        flashcardsRepository.updateCard(flashcard.copy(favoured = card.favoured))
        showResult.value = lastPage
    }

    fun toggleFavoured(flashcard: Flashcard, favoured: Boolean) = viewModelScope.launch {
        favourites.update { values ->
            values.toMutableMap()
                .apply { this[flashcard.id] = favoured }
                .toMap()
        }

        flashcardsRepository.updateCard(flashcard.copy(favoured = favoured))
    }
}
