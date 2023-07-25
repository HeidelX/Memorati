package com.memorati.feature.assistant.algorthim

import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

class AssistantAlgorithmTest {
    @Test
    fun `Repeated wrong answers doesn't set next review time in the past`() {
        val now = Clock.System.now()
        var flashcard = Flashcard(
            id = 1,
            idiom = "Hello",
            meaning = "Hallo",
            createdAt = now,
            lastReviewAt = now,
            nextReviewAt = now.plus(1.hours),
            topics = listOf(
                Topic(1, "de"),
                Topic(2, "A1"),
                Topic(3, "A2"),
            ),
            idiomLanguageTag = null,
        )
        repeat(1_000) {
            flashcard = flashcard.handleReviewResponse(isCorrect = false).scheduleNextReview()
            assertTrue { flashcard.nextReviewAt > now }
        }
    }

    @Test
    fun `Repeated correct answers puts review in long future periods`() {
        var duration = Duration.ZERO
        val now = Clock.System.now()
        var flashcard = Flashcard(
            id = 1,
            idiom = "Hello",
            meaning = "Hallo",
            createdAt = now,
            lastReviewAt = now,
            nextReviewAt = now.plus(6.hours),
            topics = listOf(
                Topic(1, "de"),
                Topic(2, "A1"),
                Topic(3, "A2"),
            ),
            idiomLanguageTag = null,
        )
        repeat(1_000) {
            flashcard = flashcard.handleReviewResponse(isCorrect = true).scheduleNextReview()
            val diff = flashcard.nextReviewAt.minus(now)
            println(diff)
            assertTrue { diff >= duration }
            assertTrue { flashcard.nextReviewAt > now }
            duration = diff
        }
    }

    @Test
    fun `Repeated correct answers changes review durations`() {
        var duration = Duration.ZERO
        val now = Clock.System.now()
        var flashcard = Flashcard(
            id = 1,
            idiom = "Hello",
            meaning = "Hallo",
            createdAt = now,
            lastReviewAt = now,
            nextReviewAt = now.plus(6.hours),
            topics = listOf(
                Topic(1, "de"),
                Topic(2, "A1"),
                Topic(3, "A2"),
            ),
            idiomLanguageTag = null,
        )
        repeat(1_000) { times ->
            val isCorrect = times % 2 == 0
            flashcard =
                flashcard.handleReviewResponse(isCorrect = isCorrect).scheduleNextReview()
            val diff = flashcard.nextReviewAt.minus(now)
            if (isCorrect) {
                assertTrue { diff > duration }
            } else {
                assertTrue { diff < duration }
            }
            println(diff)
            duration = diff
        }
    }
}
