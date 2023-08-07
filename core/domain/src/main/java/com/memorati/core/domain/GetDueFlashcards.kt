package com.memorati.core.domain

import com.memorati.core.common.time.AppTime
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.DueCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDueFlashcards @Inject constructor(
    private val appTime: AppTime,
    private val flashcardsRepository: FlashcardsRepository,
) {

    private val meanings = flashcardsRepository.flashcards().map { cards ->
        cards.map { card -> card.meaning }
    }

    operator fun invoke(): Flow<List<DueCard>> = flashcardsRepository
        .dueFlashcards(appTime.now)
        .map { dueCards ->
            dueCards.sortedWith(
                compareBy(
                    { card -> card.additionalInfo.consecutiveCorrectCount },
                    { card -> card.lastReviewAt },
                ),
            ).map { dueCard ->
                DueCard(
                    flashcard = dueCard,
                    answers = meanings.first()
                        .minus(dueCard.meaning)
                        .assistantAnswers()
                        .plus(dueCard.meaning)
                        .shuffled()
                )
            }
        }
}

internal fun List<String>.assistantAnswers(): List<String> = when {
    isEmpty() -> emptyList()
    size == 1 -> listOf(random())
    else -> asSequence().shuffled().take(3).toList()
}
