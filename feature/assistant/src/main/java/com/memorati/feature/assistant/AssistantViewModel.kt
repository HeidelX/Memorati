package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AssistantCard
import com.memorati.feature.assistant.algorthim.handleReviewResponse
import com.memorati.feature.assistant.algorthim.scheduleNextReview
import com.memorati.feature.assistant.state.AssistantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {
    private val userReviews = MutableStateFlow<Map<Long, String>>(emptyMap())
    private val backs = flashcardsRepository.flashcards().map { cards ->
        cards.map { card -> card.back }
    }
    private val flashcards =
        combine(
            flashcardsRepository.flashcardsToReview(time = Clock.System.now()),
            backs,
        ) { cards, backs ->
            // Clear user selection on new emissions, because answers will change
            userReviews.update { emptyMap() }
            cards.map { card ->
                val rest = backs.filterNot { back -> back == card.back }
                AssistantCard(
                    flashcard = card,
                    answers = rest.assistantAnswers().plus(card.back).shuffled(),
                )
            }
        }

    val state = combine(
        flashcards,
        userReviews,
    ) { cards, reviews ->
        AssistantState(
            reviews = cards.map { card ->
                card.copy(
                    response = reviews[card.flashcard.id],
                )
            },
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        AssistantState(),
    )

    fun selectOption(card: AssistantCard, selection: String) =
        userReviews.update { values ->
            values.toMutableMap()
                .apply { this[card.flashcard.id] = selection }
                .toMap()
        }

    fun updateCard(card: AssistantCard) = viewModelScope.launch {
        val flashcard = handleReviewResponse(
            flashcard = card.flashcard,
            isCorrect = card.isCorrect,
        )

        flashcardsRepository.updateCard(
            flashcard.copy(
                nextReviewAt = scheduleNextReview(flashcard),
            ),
        )
    }
}

internal fun List<String>.assistantAnswers(): List<String> = when {
    isEmpty() -> emptyList()
    size == 1 -> listOf(random())
    else -> asSequence().shuffled().take(2).toList()
}
