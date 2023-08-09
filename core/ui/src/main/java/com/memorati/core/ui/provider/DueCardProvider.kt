package com.memorati.core.ui.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.memorati.core.model.DueCard

class DueCardProvider : PreviewParameterProvider<DueCard> {
    override val values: Sequence<DueCard>
        get() = sequenceOf(dueCard)
}

class DueCardsProvider : PreviewParameterProvider<List<DueCard>> {
    override val values: Sequence<List<DueCard>>
        get() = sequenceOf(
            listOf(
                dueCard,
                dueCard,
                dueCard,
            ),
        )
}

private val dueCard = DueCard(
    flashcard = flashcard,
    answers = listOf(flashcard.meaning, "Bye Bye", "OK"),
)
