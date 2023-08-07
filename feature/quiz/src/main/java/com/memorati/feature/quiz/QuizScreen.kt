package com.memorati.feature.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.CardsStack
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardsProvider
import com.memorati.core.ui.theme.AndroidGreen
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.core.ui.theme.Moccasin

@Composable
internal fun QuizScreen(
    modifier: Modifier = Modifier,
    flashcards: List<Flashcard>,
    onSwipeCardRight: (Flashcard) -> Unit,
    onSwipeCardLeft: (Flashcard) -> Unit,
) {
    if (flashcards.isEmpty()) {
        EmptyScreen(
            resource = R.raw.quiz,
            message = stringResource(R.string.no_flashcards_yet),
        )
    } else {
        Box(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            MemorisedArrow(modifier = Modifier.align(Alignment.TopEnd))
            UnmemorisedArrow(modifier = Modifier.align(Alignment.BottomStart))
            CardsStack(
                modifier = Modifier.align(Alignment.Center),
                items = flashcards,
                onSwipeCardStart = onSwipeCardLeft,
                onSwipeCardEnd = onSwipeCardRight,
                itemKey = { card -> card.id },
                cardContent = { card -> QuizCard(card = card) },
            )
        }
    }
}

@Composable
private fun MemorisedArrow(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomStart = 15.dp, topStart = 15.dp))
            .clip(CutCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
            .background(AndroidGreen)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            text = stringResource(R.string.memorized),
            color = Color.DarkGray,
        )
    }
}

@Composable
private fun UnmemorisedArrow(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomEnd = 15.dp, topEnd = 15.dp))
            .clip(CutCornerShape(bottomStart = 30.dp, topStart = 30.dp))
            .background(Moccasin)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            text = stringResource(R.string.unmemorized),
            color = Color.DarkGray,
        )
    }
}

@DevicePreviews
@Composable
private fun QuizScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    MemoratiTheme {
        Surface {
            QuizScreen(
                flashcards = flashcards,
                onSwipeCardLeft = {},
                onSwipeCardRight = {},
            )
        }
    }
}
