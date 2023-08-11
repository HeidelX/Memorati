package com.memorati.feature.quiz.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val state = flashcardsRepository.flashcards().map { cards ->
        val flashcards = cards.sortedWith(
            compareBy(
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.difficulty },
                { card -> card.additionalInfo.memoryStrength },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(3)

        flashcards.zip(flashcards.shuffled())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )
}
