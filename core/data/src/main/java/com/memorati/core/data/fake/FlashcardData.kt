package com.memorati.core.data.fake

import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

object FlashcardData {
    val flashcard = Flashcard(
        id = 1,
        front = "Hello",
        back = "Hallo",
        createdAt = Clock.System.now(),
        lastReviewAt = Clock.System.now(),
        nextReviewAt = Clock.System.now().plus(10.days),
        topics = listOf(Topic(0, "de"), Topic(0, "A1"), Topic(0, "A2")),
    )

    val flashcards = listOf(
        flashcard,
        flashcard,
        flashcard,
        flashcard,
        flashcard,
        flashcard,
        flashcard,
    )
}
