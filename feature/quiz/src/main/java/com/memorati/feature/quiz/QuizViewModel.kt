package com.memorati.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val state = flashcardsRepository.flashcards().map { cards ->
        cards.sortedWith(
            compareBy(
                { card -> card.createdAt },
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(5)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    fun onSwipeCardLeft() {
    }

    fun onSwipeCardRight() {
    }
}
