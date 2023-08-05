package com.memorati.feature.assistant

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FavouriteButton
import com.memorati.core.model.AssistantCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.AssistantCardProvider
import com.memorati.core.ui.provider.AssistantCardsProvider
import com.memorati.core.ui.theme.MemoratiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssistantPager(
    modifier: Modifier = Modifier,
    assistantCards: List<AssistantCard>,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onUpdateCard: (AssistantCard, Boolean) -> Unit,
    onAnswerSelected: (AssistantCard, String) -> Unit,
) {
    val pagerState = rememberPagerState { assistantCards.size }
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier) {
        ProgressTitle(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            currentPage = pagerState.currentPage,
            count = pagerState.pageCount,
        )

        ReviewProgress(
            currentPage = pagerState.currentPage,
            count = pagerState.pageCount,
        )

        HorizontalPager(
            modifier = Modifier.weight(1.0f),
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            val assistantCard = assistantCards[page]
            AssistantPage(
                modifier = Modifier.fillMaxWidth(),
                card = assistantCard,
                onNext = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage.plus(1))
                        onUpdateCard(assistantCard, page.plus(1) == pagerState.pageCount)
                    }
                },
                toggleFavoured = toggleFavoured,
                onAnswerSelected = onAnswerSelected,
            )
        }
    }
}

@Composable
private fun ReviewProgress(
    modifier: Modifier = Modifier,
    currentPage: Int,
    count: Int,
) {
    val progress by animateFloatAsState(
        targetValue = currentPage.plus(1) / count.toFloat(),
        label = "FloatAnimation",
    )
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(CircleShape),
        progress = progress,
        strokeCap = StrokeCap.Round,
    )
}

@Composable
private fun ProgressTitle(
    modifier: Modifier = Modifier,
    currentPage: Int,
    count: Int,
) {
    Row(modifier = modifier) {
        Text(
            text = (currentPage + 1).toString(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        Text(
            text = stringResource(R.string.question_count, count),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        )
    }
}

@Composable
fun AssistantPage(
    modifier: Modifier = Modifier,
    card: AssistantCard,
    onNext: () -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onAnswerSelected: (AssistantCard, String) -> Unit,
) {
    Box(
        modifier = modifier.padding(
            vertical = 32.dp,
            horizontal = 16.dp,
        ),
    ) {
        FlippableCard(
            front = {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = card.flashcard.idiom,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            back = {
                AnswerRadioButtons(
                    card = card,
                    onAnswerSelected = onAnswerSelected,
                )
            },
        )

        FavouriteButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart),
            favoured = card.favoured,
            onCheckedChange = {
                toggleFavoured(card.flashcard, it)
            },
        )

        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            enabled = card.isAnswered,
            onClick = onNext,
        ) {
            Text(
                text = stringResource(R.string.next),
            )
        }
    }
}

@Composable
@DevicePreviews
private fun AssistantScreenPreview(
    @PreviewParameter(AssistantCardsProvider::class) assistantCards: List<AssistantCard>,
) {
    MemoratiTheme {
        AssistantPager(
            assistantCards = assistantCards,
            toggleFavoured = { _, _ -> },
            onUpdateCard = { _, _ -> },
            onAnswerSelected = { _, _ -> },
        )
    }
}

@Composable
@DevicePreviews
private fun AssistantItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    MemoratiTheme {
        AssistantPage(
            card = assistantCard.copy(answer = "Communication skills"),
            onNext = {},
            toggleFavoured = { _, _ -> },
            onAnswerSelected = { _, _ -> },
        )
    }
}
