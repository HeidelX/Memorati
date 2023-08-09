package com.memorati.core.domain

import com.memorati.core.common.time.AppTime
import com.memorati.core.testing.repository.TestFlashcardsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Test
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

class GetDueFlashcardsTest {

    @Test
    fun `Cards with low 'consecutiveCorrectCount' has top priority`() = runTest {
        val assistantCards = useCase().invoke().first()
        assistantCards.zipWithNext { a, b ->
            assertTrue(
                a.additionalInfo.consecutiveCorrectCount <=
                    b.additionalInfo.consecutiveCorrectCount,
            )
        }
    }

    @Test
    fun `Cards with older 'lastReviewAt' has top priority`() = runTest {
        val assistantCards = useCase().invoke().first()
        assistantCards
            .groupBy { it.additionalInfo.consecutiveCorrectCount }
            .forEach { (_, assistantCards) ->
                assistantCards.zipWithNext { a, b ->
                    assertTrue(a.lastReviewAt <= b.lastReviewAt)
                }
            }
    }

    @Test
    fun `Cards with due 'nextReviewAt' has top priority`() = runTest {
        val time = Clock.System.now().plus(1.hours)
        useCase(time).invoke().first().forEach { card ->
            assertTrue(card.nextReviewAt <= time)
        }
    }

    @Test
    fun `Cards with future 'nextReviewAt' are not retrieved`() = runTest {
        val time = Clock.System.now().minus(1.hours)
        assertTrue(useCase(time).invoke().first().isEmpty())
    }

    private fun useCase(
        time: Instant = Clock.System.now().plus(4.days),
        repository: TestFlashcardsRepository = TestFlashcardsRepository(),
    ) = GetDueFlashcards(
        appTime = object : AppTime {
            override val now: Instant get() = time
        },
        flashcardsRepository = repository,
    )
}
