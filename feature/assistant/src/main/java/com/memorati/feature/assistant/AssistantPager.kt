package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FavouriteButton
import com.memorati.core.model.AssistantCard
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.provider.AssistantCardProvider
import com.memorati.core.ui.provider.AssistantCardsProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssistantPager(
    modifier: Modifier = Modifier,
    assistantCards: List<AssistantCard>,
    onUpdateCard: (AssistantCard, Boolean) -> Unit,
    onAnswerSelected: (AssistantCard, String) -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
) {
    val pagerState = rememberPagerState { assistantCards.size }
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier.weight(1.0f),
            state = pagerState,
            contentPadding = PaddingValues(24.dp),
            userScrollEnabled = false,
        ) { page ->
            val assistantCard = assistantCards[page]
            AssistantPage(
                modifier = Modifier.fillMaxWidth(),
                page = page + 1,
                card = assistantCard,
                onAnswerSelected = onAnswerSelected,
                onNext = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage.plus(1),
                        )
                        onUpdateCard(assistantCard, page.plus(1) == pagerState.pageCount)
                    }
                },
                toggleFavoured = toggleFavoured,
            )
        }

        ReviewProgress(
            currentPage = pagerState.currentPage,
            count = pagerState.pageCount,
        )
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
            .padding(16.dp)
            .height(8.dp)
            .clip(CircleShape),
        progress = progress,
    )
}

@Composable
fun AssistantPage(
    modifier: Modifier = Modifier,
    page: Int,
    card: AssistantCard,
    onNext: () -> Unit,
    toggleFavoured: (Flashcard, Boolean) -> Unit,
    onAnswerSelected: (AssistantCard, String) -> Unit,
) {
    val color = MaterialTheme.colorScheme.primary
    Surface(
        modifier
            .padding(10.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(30.dp))
            .background(color)
            .padding(16.dp),
        color = color,
    ) {
        Box {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = card.flashcard.front,
                    style = MaterialTheme.typography.titleLarge,
                )
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 10.dp),
                )

                card.answers.forEach { answer ->
                    AnswerRadioButton(card, answer, onAnswerSelected)
                }
                AnswerIcon(card)

                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.End),
                    visible = card.isAnswered,
                ) {
                    OutlinedButton(onClick = onNext) {
                        Text(
                            text = stringResource(R.string.next),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }


            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = page.toString(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Monospace,
            )

            FavouriteButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                favoured = card.favoured,
                onCheckedChange = {
                    toggleFavoured(card.flashcard, it)
                },
            )
        }
    }
}

@Composable
private fun ColumnScope.AnswerIcon(card: AssistantCard) {
    AnimatedVisibility(
        modifier = Modifier
            .padding(top = 24.dp)
            .size(60.dp)
            .align(Alignment.CenterHorizontally),
        visible = card.isAnswered,
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Black)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            Color.White,
                            Color.DarkGray,
                        ),
                    ),
                    shape = CircleShape,
                )
                .padding(5.dp),
            imageVector = if (card.isCorrect) MemoratiIcons.Done else MemoratiIcons.Close,
            contentDescription = stringResource(
                id = if (card.isCorrect) R.string.correct_result else R.string.wrong_result,
            ),
            colorFilter = ColorFilter.tint(
                if (card.isCorrect) Color.Green else Color.Red,
            ),
        )
    }
}

@Composable
private fun AnswerRadioButton(
    card: AssistantCard,
    answer: String,
    onOptionSelected: (AssistantCard, String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                enabled = !card.isAnswered,
                selected = card.answer == answer,
                onClick = {
                    onOptionSelected(card, answer)
                },
            )
            .border(
                width = 2.dp,
                color = if (card.flashcard.back == answer && card.isAnswered) {
                    Color.Green.copy(alpha = 0.7f)
                } else {
                    Color.Transparent
                },
                shape = CircleShape,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            enabled = !card.isAnswered,
            selected = card.answer == answer,
            onClick = {
                onOptionSelected(card, answer)
            },
            colors = RadioButtonDefaults.colors(
                unselectedColor = MaterialTheme.colorScheme.onPrimary,
                selectedColor = MaterialTheme.colorScheme.onTertiaryContainer,
                disabledUnselectedColor = MaterialTheme.colorScheme.onPrimary,
                disabledSelectedColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ),
        )
        Text(
            text = answer,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
@Preview
private fun AssistantScreenPreview(
    @PreviewParameter(AssistantCardsProvider::class) assistantCards: List<AssistantCard>,
) {
    AssistantPager(
        assistantCards = assistantCards,
        toggleFavoured = { _, _ -> },
        onUpdateCard = { _, _ -> },
        onAnswerSelected = { _, _ -> },
    )
}

@Composable
@Preview
private fun AssistantItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantPage(
        card = assistantCard.copy(answer = "Hallo"),
        page = 1,
        onNext = {},
        toggleFavoured = { _, _ -> },
        onAnswerSelected = { _, _ -> },
    )
}

@Composable
@Preview
private fun AssistantWrongItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantPage(
        card = assistantCard.copy(answer = "OK"),
        page = 1,
        onNext = {},
        toggleFavoured = { _, _ -> },
        onAnswerSelected = { _, _ -> },
    )
}

@Composable
@Preview
private fun AssistantNoAnswerItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantPage(
        card = assistantCard,
        page = 1,
        onNext = {},
        toggleFavoured = { _, _ -> },
        onAnswerSelected = { _, _ -> },
    )
}
