package com.memorati.feature.quiz.knowledge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.review
import com.memorati.core.common.map.mutate
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.core.model.Flashcard
import com.memorati.feature.quiz.knowledge.model.KnowledgeCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class KnowledgeDirectionsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    private val preferencesDataSource: PreferencesDataSource,
) : ViewModel() {

    private val flips = MutableStateFlow(mapOf<Long, Boolean>())
    val state = combine(
        flashcardsRepository.flashcards(),
        flips,
    ) { cards, flips ->
        cards.sortedWith(
            compareBy(
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.difficulty },
                { card -> card.additionalInfo.memoryStrength },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(3)
            .reversed()
            .map { flashcard ->
                KnowledgeCard(
                    flashcard = flashcard,
                    flipped = flips[flashcard.id] ?: false,
                )
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    fun onSwipeCardStart(card: Flashcard) = launch {
        updateCard(card = card, correct = false)
    }

    fun onSwipeCardEnd(card: Flashcard) = launch {
        updateCard(card = card, correct = true)
    }

    fun toggleFavoured(card: Flashcard, favoured: Boolean) = launch {
        flashcardsRepository.updateCard(card.copy(favoured = favoured))
    }

    fun onFlip(card: Flashcard, flip: Boolean) {
        flips.update { flips -> flips.mutate { put(card.id, flip) } }
    }

    private suspend fun updateCard(card: Flashcard, correct: Boolean) {
        val userData = preferencesDataSource.userData.first()
        flashcardsRepository.updateCard(
            card.review(
                answerCorrect = correct,
                wordCorrectnessCount = userData.wordCorrectnessCount,
                weeksOfReview = userData.weeksOfReview,
            ),
        )
    }
}
