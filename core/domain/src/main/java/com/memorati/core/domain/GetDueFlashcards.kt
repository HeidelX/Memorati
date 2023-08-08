package com.memorati.core.domain

import com.memorati.core.common.time.AppTime
import com.memorati.core.data.repository.FlashcardsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDueFlashcards @Inject constructor(
    private val appTime: AppTime,
    private val flashcardsRepository: FlashcardsRepository,
) {

    operator fun invoke() = flashcardsRepository
        .dueFlashcards(appTime.now)
        .map { dueCards ->
            dueCards.sortedWith(
                compareBy(
                    { card -> card.additionalInfo.consecutiveCorrectCount },
                    { card -> card.lastReviewAt },
                ),
            )
        }
}
