package com.memorati.core.ui.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

class FlashcardsProvider : PreviewParameterProvider<List<Flashcard>> {
    override val values: Sequence<List<Flashcard>>
        get() = sequenceOf(flashcards)
}

class FlashcardProvider : PreviewParameterProvider<Flashcard> {
    override val values: Sequence<Flashcard>
        get() = sequenceOf(flashcard)
}

internal val flashcard = Flashcard(
    id = 1,
    front = "Hello",
    back = "Hallo",
    createdAt = Clock.System.now(),
    lastReviewAt = Clock.System.now(),
    nextReviewAt = Clock.System.now().plus(10.days),
    topics = listOf(
        Topic(1, "de"),
        Topic(2, "A1"),
        Topic(3, "A2"),
    ),
)

internal val flashcards = listOf(
    flashcard,
    flashcard.copy(id = 2),
    flashcard.copy(id = 3),
)
