package com.memorati.core.domain

import com.memorati.core.common.time.AppTime
import com.memorati.core.testing.repository.TestFlashcardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours

class GetDueFlashcardsTest {

    @Test
    fun `Cards with low 'consecutiveCorrectCount' has top priority`() = runTest {
        val assistantCards = useCase().invoke().first()
        assertTrue(assistantCards.size == 30)
        assistantCards.zipWithNext { a, b ->
            assertTrue(
                a.flashcard.additionalInfo.consecutiveCorrectCount <=
                    b.flashcard.additionalInfo.consecutiveCorrectCount,
            )
        }
    }

    @Test
    fun `Cards with older 'lastReviewAt' has top priority`() = runTest {
        val assistantCards = useCase().invoke().first()
        assertTrue(assistantCards.size == 30)
        assistantCards
            .groupBy { it.flashcard.additionalInfo.consecutiveCorrectCount }
            .forEach { (_, assistantCards) ->
                assistantCards.zipWithNext { a, b ->
                    assertTrue(a.flashcard.lastReviewAt <= b.flashcard.lastReviewAt)
                }
            }
    }

    @Test
    fun `Cards with due 'nextReviewAt' has top priority`() = runTest {
        val time = Clock.System.now().plus(1.hours)
        useCase(time).invoke().first().forEach { card ->
            assertTrue(card.flashcard.nextReviewAt <= time)
        }
    }

    @Test
    fun `Cards with future 'nextReviewAt' are not retrieved`() = runTest {
        val time = Clock.System.now().minus(1.hours)
        assertTrue(useCase(time).invoke().first().isEmpty())
    }

    @Test
    fun `30 Cards are returned per review`() = runTest {
        assertTrue(useCase().invoke().first().size == 30)
    }

    @Test
    fun `Due cards change after review patches`() = runTest {
        val flashcardsRepository = TestFlashcardsRepository()
        while (useCase(repository = flashcardsRepository).invoke().first().isNotEmpty()) {
            val patch = useCase(repository = flashcardsRepository).invoke().first()
            assertTrue(patch.size <= 30)
            patch.forEach { card ->
                flashcardsRepository.updateCard(
                    card.flashcard.copy(
                        lastReviewAt = Clock.System.now().plus(1.hours),
                        additionalInfo = card.flashcard.additionalInfo.copy(
                            consecutiveCorrectCount =
                            card.flashcard.additionalInfo.consecutiveCorrectCount + 1,
                        ),
                        nextReviewAt = Clock.System.now().plus(10.hours),
                    ),
                )
            }

            val nextPatch = useCase(repository = flashcardsRepository).invoke().first()
            assertTrue(nextPatch.size <= 30)
            assertNotEquals(patch, nextPatch)
            if (nextPatch.isNotEmpty()) {
                assertTrue(
                    patch.last().flashcard.additionalInfo.consecutiveCorrectCount <=
                        nextPatch.first().flashcard.additionalInfo.consecutiveCorrectCount,
                )
            }
        }
    }

    private fun useCase(
        time: Instant = Clock.System.now().plus(1.hours),
        repository: TestFlashcardsRepository = TestFlashcardsRepository(),
    ) = GetDueFlashcards(
        appTime = object : AppTime {
            override val now: Instant get() = time
        },
        flashcardsRepository = repository,
        ioDispatcher = Dispatchers.Unconfined,
    )
}
