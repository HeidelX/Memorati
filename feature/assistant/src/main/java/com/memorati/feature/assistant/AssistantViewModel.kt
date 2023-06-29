package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.dispatcher.Dispatcher
import com.memorati.core.common.dispatcher.MemoratiDispatchers.IO
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AssistantCard
import com.memorati.feature.assistant.algorthim.handleReviewResponse
import com.memorati.feature.assistant.algorthim.scheduleNextReview
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val userReviews = MutableStateFlow<Map<Long, String>>(emptyMap())
    private val showResult = MutableStateFlow(false)
    private val flashcards = flow {
        userReviews.update { emptyMap() }
        showResult.update { false }
        val cards = withContext(ioDispatcher) {
            val backs = flashcardsRepository.flashcards()
                .map { cards -> cards.map { card -> card.back } }
                .first()
            flashcardsRepository.flashcardsToReview(time = Clock.System.now())
                .take(30)
                .map { card ->
                    val rest = backs.filterNot { back -> back == card.back }
                    AssistantCard(
                        flashcard = card,
                        answers = rest.assistantAnswers().plus(card.back).shuffled(),
                    )
                }
        }
        emit(cards)
    }

    val state = combine(
        flashcards,
        userReviews,
        showResult,
    ) { cards, reviews, showResult ->

        val reviewedCards = cards.map { card ->
            card.copy(response = reviews[card.flashcard.id])
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

    fun selectOption(card: AssistantCard, selection: String) =
        userReviews.update { values ->
            values.toMutableMap()
                .apply { this[card.flashcard.id] = selection }
                .toMap()
        }

    fun updateCard(card: AssistantCard, lastPage: Boolean) = viewModelScope.launch {
        val flashcard = card.flashcard.handleReviewResponse(card.isCorrect).scheduleNextReview()
        flashcardsRepository.updateCard(flashcard)
        showResult.value = lastPage
    }
}

internal fun List<String>.assistantAnswers(): List<String> = when {
    isEmpty() -> emptyList()
    size == 1 -> listOf(random())
    else -> asSequence().shuffled().take(2).toList()
}
