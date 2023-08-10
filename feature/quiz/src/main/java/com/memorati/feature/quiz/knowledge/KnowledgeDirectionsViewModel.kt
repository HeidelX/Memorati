package com.memorati.feature.quiz.knowledge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.answer
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class KnowledgeDirectionsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val state = flashcardsRepository.flashcards().map { cards ->
        cards.sortedWith(
            compareBy(
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.difficulty },
                { card -> card.additionalInfo.memoryStrength },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(3).reversed()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    fun onSwipeCardLeft(card: Flashcard) = launch {
        updateCard(card = card, correct = false)
    }

    fun onSwipeCardRight(card: Flashcard) = launch {
        updateCard(card = card, correct = true)
    }

    private suspend fun updateCard(card: Flashcard, correct: Boolean) {
        flashcardsRepository.updateCard(card.answer(correct))
    }
}
