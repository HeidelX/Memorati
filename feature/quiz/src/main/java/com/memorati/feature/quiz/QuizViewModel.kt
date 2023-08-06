package com.memorati.feature.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val state = flashcardsRepository.flashcards().map { cards ->
        cards.sortedWith(
            compareBy { card -> card.lastReviewAt },
        ).take(3).reversed()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    fun onSwipeCardLeft(card: Flashcard) = launch {
        Log.d("QuizViewModel", "onSwipeCardLeft")
        flashcardsRepository.updateCard(
            card.copy(
                lastReviewAt = Clock.System.now(),
            ),
        )
    }

    fun onSwipeCardRight(card: Flashcard) = launch {
        Log.d("QuizViewModel", "onSwipeCardRight")
        flashcardsRepository.updateCard(
            card.copy(
                lastReviewAt = Clock.System.now(),
            ),
        )
    }
}
