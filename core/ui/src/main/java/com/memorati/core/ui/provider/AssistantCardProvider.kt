package com.memorati.core.ui.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.memorati.core.model.AssistantCard

class AssistantCardProvider : PreviewParameterProvider<AssistantCard> {
    override val values: Sequence<AssistantCard>
        get() = sequenceOf(assistantCard)
}

class AssistantCardsProvider : PreviewParameterProvider<List<AssistantCard>> {
    override val values: Sequence<List<AssistantCard>>
        get() = sequenceOf(
            listOf(
                assistantCard,
                assistantCard,
                assistantCard,
            ),
        )
}

private val assistantCard = AssistantCard(
    flashcard = flashcard,
    answers = listOf("Hello", "Bye Bye", "OK"),
)
