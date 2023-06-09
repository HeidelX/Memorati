package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.memorati.core.model.AssistantCard
import com.memorati.core.ui.provider.AssistantCardProvider
import com.memorati.core.ui.provider.AssistantCardsProvider
import com.memorati.feature.assistant.state.AssistantState
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssistantPager(
    modifier: Modifier = Modifier,
    assistantCards: List<AssistantCard>,
    onOptionSelected: (Long, String) -> Unit,
) {
    val pagerState = rememberPagerState { assistantCards.size }
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
    ) { page ->
        AssistantPage(
            modifier = Modifier
                .pageTransition(pagerState, page)
                .fillMaxWidth(),
            card = assistantCards[page],
            onOptionSelected = onOptionSelected,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.pageTransition(
    pagerState: PagerState,
    page: Int,
) = graphicsLayer {
    // Calculate the absolute offset for the current page from the
    // scroll position. We use the absolute value which allows us to mirror
    // any effects for both directions
    val pageOffset = (
        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        ).absoluteValue

    // We animate the alpha, between 50% and 100%
    alpha = lerp(
        start = 0.5f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f),
    )

    // We animate the scaleX + scaleY, between 85% and 100%
    lerp(
        start = 0.85f,
        stop = 1f,
        fraction = 1f - pagerState.currentPageOffsetFraction.absoluteValue.coerceIn(
            0f,
            1f,
        ),
    ).also { scale ->
        scaleX = scale
        scaleY = scale
    }
}

@Composable
fun AssistantPage(
    modifier: Modifier = Modifier,
    card: AssistantCard,
    onOptionSelected: (Long, String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(30.dp))
            .aspectRatio(1f),
    ) {
        val color = MaterialTheme.colorScheme.primary
        Surface(
            Modifier
                .background(color)
                .padding(24.dp)
                .fillMaxSize(),
            color = color,
        ) {
            Column {
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                enabled = !card.isAnswered,
                                selected = card.response == answer,
                                onClick = {
                                    onOptionSelected(card.flashcard.id, answer)
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
                            selected = card.response == answer,
                            onClick = {
                                onOptionSelected(card.flashcard.id, answer)
                            },
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colorScheme.onPrimary,
                                selectedColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            ),
                        )
                        Text(
                            text = answer,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

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
                        imageVector = if (card.isCorrect) {
                            MemoratiIcons.Done
                        } else {
                            MemoratiIcons.Close
                        },
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            if (card.isCorrect) Color.Green else Color.Red,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
internal fun AssistantScreenPreview(
    @PreviewParameter(AssistantCardsProvider::class) assistantCards: List<AssistantCard>,
) {
    AssistantScreen(state = AssistantState(reviews = assistantCards)) { _, _ -> }
}

@Composable
@Preview
fun AssistantItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantPage(
        card = assistantCard.copy(response = "Hallo"),
        onOptionSelected = { _, _ -> },
    )
}

@Composable
@Preview
fun AssistantWrongItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantPage(
        card = assistantCard.copy(response = "OK"),
        onOptionSelected = { _, _ -> },
    )
}

@Composable
@Preview
fun AssistantNoAnswerItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantPage(
        card = assistantCard,
        onOptionSelected = { _, _ -> },
    )
}
