package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.di.ApplicationScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AssistantCard
import com.memorati.core.model.Flashcard
import com.memorati.feature.assistant.algorthim.handleReviewResponse
import com.memorati.feature.assistant.algorthim.scheduleNextReview
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    @ApplicationScope private val scope: CoroutineScope,
) : ViewModel() {

    private val userAnswers = MutableStateFlow<Map<Long, String>>(emptyMap())
    private val reviewResult = MutableStateFlow<Review?>(null)
    private val answersSets = mutableMapOf<Long, List<String>>()
    private val patch = mutableSetOf<Flashcard>()
    private val backs = flashcardsRepository.flashcards().map { cards ->
        cards.map { card -> card.back }
    }

    private val flashcards = flashcardsRepository.flashcardsToReview(
        time = Clock.System.now(),
    ).map { cardsToReview ->
        cardsToReview
            .take(30)
            .map { card ->
                val rest = backs.first().filterNot { back -> back == card.back }
                AssistantCard(
                    flashcard = card,
                    answers = answersSets[card.id] ?: rest.assistantAnswers().plus(card.back)
                        .shuffled().also {
                            answersSets[card.id] = it
                        },
                )
            }
    }

    private val reviewedCards = combine(
        flashcards,
        userAnswers,
    ) { cards, reviews ->
        cards.map { card ->
            card.copy(response = reviews[card.flashcard.id])
        }
    }

    val state = combine(
        reviewedCards,
        reviewResult,
    ) { reviewedCards, result ->
        when {
            result != null -> ReviewResult(
                correctAnswers = result.correctAnswers,
                wrongAnswers = result.wrongAnswers,
            )
            
            reviewedCards.isEmpty() -> EmptyState

            else -> AssistantCards(reviews = reviewedCards)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyState,
    )

    fun selectAnswer(card: AssistantCard, answer: String) =
        userAnswers.update { values ->
            values.toMutableMap().apply { this[card.flashcard.id] = answer }.toMap()
        }

    fun updateCard(card: AssistantCard, lastPage: Boolean) = viewModelScope.launch {
        patch.add(card.flashcard.handleReviewResponse(card.isCorrect).scheduleNextReview())
        if (lastPage) {
            val assistantCards = reviewedCards.first()
            reviewResult.update {
                Review(
                    correctAnswers = assistantCards.count { it.isCorrect },
                    wrongAnswers = assistantCards.count { !it.isCorrect },
                )
            }

            scope.launch {
                flashcardsRepository.updateCards(patch.toList())
                patch.clear()
                answersSets.clear()
                userAnswers.update { emptyMap() }
            }
        }
    }

    fun toggleFavoured(flashcard: Flashcard) {
        viewModelScope.launch {
            flashcardsRepository.updateCard(flashcard.copy(favoured = !flashcard.favoured))
        }
    }
}

private data class Review(
    val correctAnswers: Int,
    val wrongAnswers: Int,
)

internal fun List<String>.assistantAnswers(): List<String> = when {
    isEmpty() -> emptyList()
    size == 1 -> listOf(random())
    else -> asSequence().shuffled().take(2).toList()
}
