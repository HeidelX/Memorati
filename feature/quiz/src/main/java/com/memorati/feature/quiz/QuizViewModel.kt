package com.memorati.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val state = flashcardsRepository.flashcards().map { cards ->
        cards.sortedWith(
            compareBy(
                { card -> card.createdAt },
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(6)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    fun onSwipeCardLeft(card: Flashcard) = launch {
        flashcardsRepository.updateCard(
            card.copy(
                additionalInfo = card.additionalInfo.copy(
                    consecutiveCorrectCount = card.additionalInfo.consecutiveCorrectCount - 1,
                ),
            ),
        )
    }

    fun onSwipeCardRight(card: Flashcard) = launch {
        flashcardsRepository.updateCard(
            card.copy(
                additionalInfo = card.additionalInfo.copy(
                    consecutiveCorrectCount = card.additionalInfo.consecutiveCorrectCount + 1,
                ),
            ),
        )
    }
}
