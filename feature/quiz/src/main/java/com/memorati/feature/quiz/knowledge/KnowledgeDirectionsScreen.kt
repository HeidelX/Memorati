package com.memorati.feature.quiz.knowledge

import MemoratiIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.CardsStack
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardsProvider
import com.memorati.core.ui.theme.AndroidGreen
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.core.ui.theme.Moccasin
import com.memorati.feature.quiz.R

@Composable
internal fun KnowledgeDirectionsScreen(
    modifier: Modifier = Modifier,
    flashcards: List<Flashcard>,
    onSwipeCardRight: (Flashcard) -> Unit,
    onSwipeCardLeft: (Flashcard) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        MemorisedDirection(modifier = Modifier.align(Alignment.TopEnd))
        UnmemorisedDirection(modifier = Modifier.align(Alignment.BottomStart))
        CardsStack(
            modifier = Modifier.align(Alignment.Center),
            items = flashcards,
            onSwipeCardStart = {
                onSwipeCardLeft(it)
                true
            },
            onSwipeCardEnd = {
                onSwipeCardRight(it)
                true
            },
            itemKey = { card -> card.id },
            cardContent = { card -> KnowledgeDirectionsCard(card = card) },
        )
    }
}

@Composable
private fun MemorisedDirection(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(text = stringResource(R.string.memorized))
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            modifier = Modifier
                .background(AndroidGreen, CircleShape)
                .padding(10.dp),
            imageVector = MemoratiIcons.ArrowForward,
            contentDescription = stringResource(R.string.memorized),
        )
    }
}

@Composable
private fun UnmemorisedDirection(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Image(
            modifier = Modifier
                .background(Moccasin, CircleShape)
                .padding(10.dp),
            imageVector = MemoratiIcons.ArrowBack,
            contentDescription = stringResource(R.string.unmemorized),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(R.string.unmemorized))
    }
}

@DevicePreviews
@Composable
private fun KnowledgeDirectionsScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    MemoratiTheme {
        Surface {
            KnowledgeDirectionsScreen(
                flashcards = flashcards,
                onSwipeCardLeft = {},
                onSwipeCardRight = {},
            )
        }
    }
}
