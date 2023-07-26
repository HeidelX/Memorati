package com.memorati.core.domain

import com.memorati.core.testing.repository.FakeFlashcardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

class GetDueFlashcardsTest {

    @Test
    fun `Cards with low 'consecutiveCorrectCount' has top priority`() = runTest {
        useCase().first().zipWithNext { a, b ->
            assertTrue(
                a.flashcard.additionalInfo.consecutiveCorrectCount <=
                    b.flashcard.additionalInfo.consecutiveCorrectCount,
            )
        }
    }

    @Test
    fun `Cards with older 'lastReviewAt' has top priority`() = runTest {
        useCase().first()
            .groupBy { it.flashcard.additionalInfo.consecutiveCorrectCount }
            .forEach { (_, assistantCards) ->
                assistantCards.zipWithNext { a, b ->
                    assertTrue(a.flashcard.lastReviewAt <= b.flashcard.lastReviewAt)
                }
            }
    }

    @Test
    fun `Cards with due 'nextReviewAt' has top priority`() = runTest {
    }

    @Test
    fun `30 Cards are returned per review`() = runTest {
        assertTrue(useCase().first().size == 30)
    }

    private val useCase
        get() = GetDueFlashcards(
            flashcardsRepository = FakeFlashcardsRepository(),
            ioDispatcher = Dispatchers.Unconfined,
        )
}
