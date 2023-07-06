package com.memorati.feature.assistant

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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
    Surface {
        Box(
            modifier = modifier.padding(
                vertical = 32.dp,
                horizontal = 16.dp,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = card.flashcard.front,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.height(16.dp))
                card.answers.forEach { answer ->
                    AnswerRadioButton(
                        modifier = Modifier.padding(vertical = 5.dp),
                        card = card,
                        answer = answer,
                        onOptionSelected = onAnswerSelected,
                    )
                }
            }

            FavouriteButton(
                modifier = Modifier.align(Alignment.BottomStart),
                favoured = card.favoured,
                onCheckedChange = {
                    toggleFavoured(card.flashcard, it)
                },
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
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
}

@Composable
private fun AnswerRadioButton(
    modifier: Modifier = Modifier,
    card: AssistantCard,
    answer: String,
    onOptionSelected: (AssistantCard, String) -> Unit,
) {
    val selected = answer == card.answer
    Surface(
        shape = MaterialTheme.shapes.large,
        color = surfaceColor(card, answer),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor(card, answer),
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .selectable(
                enabled = !card.isAnswered,
                selected = selected,
                onClick = {
                    onOptionSelected(card, answer)
                },
                role = Role.RadioButton,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(answer, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)

            Box(Modifier.padding(8.dp)) {
                RadioButton(selected, onClick = null)
            }
        }
    }
}

@Composable
private fun surfaceColor(card: AssistantCard, answer: String): Color {
    val selected = card.answer == answer
    return if (card.isAnswered) {
        if (card.flashcard.back == answer) {
            Color.Green.copy(alpha = 0.4f)
        } else if (selected && !card.isCorrect) {
            MaterialTheme.colorScheme.errorContainer
        } else {
            MaterialTheme.colorScheme.surface
        }
    } else {
        MaterialTheme.colorScheme.surface
    }
}

@Composable
private fun borderColor(card: AssistantCard, answer: String): Color {
    val selected = card.answer == answer
    return if (card.isAnswered) {
        if (card.flashcard.back == answer) {
            Color.Green
        } else if (selected && !card.isCorrect) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.outline
        }
    } else {
        MaterialTheme.colorScheme.outline
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
        card = assistantCard.copy(answer = "Communication skills"),
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
        onNext = {},
        toggleFavoured = { _, _ -> },
        onAnswerSelected = { _, _ -> },
    )
}
