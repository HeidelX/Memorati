package com.memorati.feature.quiz.typing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.review
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TypingViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    private val preferencesDataSource: PreferencesDataSource,
) : ViewModel() {

    val state = flashcardsRepository
        .flashcards()
        .map { cards ->
            cards.sortedWith(
                compareBy(
                    { card -> card.lastReviewAt },
                    { card -> card.additionalInfo.consecutiveCorrectCount },
                ),
            ).take(3).reversed()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList(),
        )

    fun swipe(
        card: Flashcard,
        correct: Boolean,
    ) = launch {
        val userData = preferencesDataSource.userData.first()
        flashcardsRepository.updateCard(
            card.review(
                isCorrect = correct,
                wordCorrectnessCount = userData.wordCorrectnessCount,
                weeksOfReview = userData.weeksOfReview,
            ),
        )
    }
}
