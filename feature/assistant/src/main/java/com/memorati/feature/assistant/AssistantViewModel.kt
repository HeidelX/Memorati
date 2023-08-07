package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.answer
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.domain.GetDueFlashcards
import com.memorati.core.model.DueCard
import com.memorati.core.model.Flashcard
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.EmptyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    getDueFlashcards: GetDueFlashcards,
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val answers = MutableStateFlow(mapOf<Long, String>())
    private val dueCards = getDueFlashcards()

    val state = combine(
        dueCards,
        answers,
    ) { dueCards, answers ->
        when {
            dueCards.isEmpty() -> EmptyState
            else -> {
                val answeredDues = dueCards
                    .take(3)
                    .map { card ->
                        card.copy(answer = answers[card.flashcard.id])
                    }.reversed()
                AssistantCards(dueCards = answeredDues)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyState,
    )

    fun onAnswerSelected(card: DueCard, selection: String) =
        answers.update { values ->
            values.toMutableMap()
                .apply { this[card.flashcard.id] = selection }
                .toMap()
        }

    fun updateCard(
        card: DueCard
    ) = launch {
        flashcardsRepository.updateCard(
            card.flashcard.answer(card.isCorrect)
        )
    }

    fun toggleFavoured(
        flashcard: Flashcard,
        favoured: Boolean
    ) = launch {
        flashcardsRepository.updateCard(
            flashcard.copy(favoured = favoured)
        )
    }
}
