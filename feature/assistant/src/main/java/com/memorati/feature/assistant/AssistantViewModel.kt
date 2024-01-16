package com.memorati.feature.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.review
import com.memorati.core.common.map.mutate
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.core.domain.GetDueFlashcards
import com.memorati.core.model.DueCard
import com.memorati.core.model.Flashcard
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewCardsStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    getDueFlashcards: GetDueFlashcards,
    private val flashcardsRepository: FlashcardsRepository,
    private val preferencesDataSource: PreferencesDataSource,
) : ViewModel() {

    private val meanings = flashcardsRepository.flashcards().map { cards ->
        cards.map { card -> card.meaning }
    }
    private val flips = MutableStateFlow(mapOf<Long, Boolean>())
    private val userAnswer = MutableStateFlow(mapOf<Long, String>())
    private val cachedAnswers = MutableStateFlow(mapOf<Long, List<String>>())
    private val dueCards = getDueFlashcards()
    private val allCards = flashcardsRepository.flashcards()

    val state = combine(
        flips,
        allCards,
        dueCards,
        userAnswer,
        cachedAnswers,
    ) { flips, allCards, dueCards, userAnswer, answers ->
        when {
            dueCards.isNotEmpty() -> {
                val answeredDues = dueCards
                    .take(3)
                    .map { card ->
                        DueCard(
                            flashcard = card,
                            answer = userAnswer[card.id],
                            answers = answers[card.id] ?: generateAnswers(card),
                            flipped = flips[card.id] ?: false,
                        )
                    }.reversed()

                AssistantCards(
                    dueCards = answeredDues,
                    dueCardsCount = dueCards.count(),
                )
            }

            allCards.isNotEmpty() -> {
                ReviewCardsStats(
                    totalCount = allCards.size,
                    reiteratedAccuracy = allCards.count { card ->
                        card.additionalInfo.consecutiveCorrectCount >= 2
                    },
                    soloAccuracy = allCards.count { card ->
                        card.additionalInfo.consecutiveCorrectCount == 1
                    },
                    zeroAccuracy = allCards.count { card ->
                        card.additionalInfo.consecutiveCorrectCount == 0
                    },
                )
            }

            else -> EmptyState
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyState,
    )

    fun onAnswerSelected(card: DueCard, selection: String) =
        userAnswer.update {
            it.mutate { this[card.flashcard.id] = selection }
        }

    fun updateCard(
        card: DueCard,
    ) = launch {
        val userData = preferencesDataSource.userData.first()
        flashcardsRepository.updateCard(
            card.flashcard.review(
                answerCorrect = card.isCorrect,
                wordCorrectnessCount = userData.wordCorrectnessCount,
                weeksOfReview = userData.weeksOfReview,
            ),
        )
    }

    fun toggleFavoured(
        flashcard: Flashcard,
        favoured: Boolean,
    ) = launch {
        flashcardsRepository.updateCard(
            flashcard.copy(favoured = favoured),
        )
    }

    fun onFlip(card: DueCard, flip: Boolean) {
        flips.update { flips -> flips.mutate { put(card.flashcard.id, flip) } }
    }

    private suspend fun generateAnswers(card: Flashcard): List<String> = meanings
        .first()
        .randomAnswersPlus(card.meaning)
        .also { answers ->
            cachedAnswers.update { cachedAnswers -> cachedAnswers.mutate { put(card.id, answers) } }
        }
}

internal fun List<String>.randomAnswersPlus(
    answer: String,
): List<String> {
    return minus(answer).assistantAnswers().plus(answer).shuffled()
}

internal fun List<String>.assistantAnswers(): List<String> = when {
    isEmpty() -> emptyList()
    size == 1 -> listOf(random())
    else -> asSequence().shuffled().take(2).toList()
}
