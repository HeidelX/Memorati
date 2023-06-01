package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AssistantCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {
    val assistantCards = flashcardsRepository.flashcards()
        .map { cards ->
            cards.map { card ->
                val rest = cards.toMutableList()
                    .apply { remove(card) }

                val assistedAnswers = when {
                    rest.isEmpty() -> emptyList()
                    rest.size == 1 -> listOf(rest.random().back)
                    else -> listOf(rest.random().back, rest.random().back).toSet().toList()
                }
                AssistantCard(
                    flashcard = card,
                    answers = (assistedAnswers + card.back).shuffled(),
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList(),
        )
}
