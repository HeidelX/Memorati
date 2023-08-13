package com.memorati.feature.quiz.knowledge

import com.memorati.core.design.icon.MemoratiIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.memorati.feature.quiz.knowledge.model.KnowledgeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun KnowledgeDirectionsScreen(
    modifier: Modifier = Modifier,
    knowledgeCards: List<KnowledgeCard>,
    onBack: () -> Unit,
    onFlip: (Flashcard, Boolean) -> Unit,
    onSwipeCardStart: (Flashcard) -> Unit,
    onSwipeCardEnd: (Flashcard) -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.knowledge_directions))
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = MemoratiIcons.ArrowBack,
                        contentDescription = stringResource(id = R.string.return_back),
                    )
                }
            },
        )

        MemorisedDirection(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
        )
        CardsStack(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
            items = knowledgeCards,
            onSwipeCardStart = { knowledgeCard ->
                onSwipeCardStart(knowledgeCard.flashcard)
                true
            },
            onSwipeCardEnd = { knowledgeCard ->
                onSwipeCardEnd(knowledgeCard.flashcard)
                true
            },
            itemKey = { card -> card },
            cardContent = { card ->
                KnowledgeDirectionsCard(
                    card = card.flashcard,
                    initialFlipped = card.flipped,
                    onFlip = onFlip,
                    toggleFavoured = toggleFavoured,
                )
            },
        )
        UnmemorisedDirection(
            modifier = Modifier.padding(16.dp),
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
                knowledgeCards = flashcards.map { flashcard ->
                    KnowledgeCard(
                        flashcard = flashcard,
                        flipped = false,
                    )
                },
                onBack = {},
                onFlip = { _, _ -> },
                onSwipeCardEnd = {},
                onSwipeCardStart = {},
                toggleFavoured = { _, _ -> },
            )
        }
    }
}
