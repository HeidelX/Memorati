package com.memorati.feature.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.AndroidGreen
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
fun QuizRoute(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
    openTyping: () -> Unit,
    openMatching: () -> Unit,
    openKnowledgeDirections: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    QuizScreen(
        modifier = modifier,
        state = state,
        openTyping = openTyping,
        openMatching = openMatching,
        openKnowledgeDirections = openKnowledgeDirections,
    )
}

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    state: Boolean,
    openTyping: () -> Unit,
    openMatching: () -> Unit,
    openKnowledgeDirections: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (state) {
            EmptyScreen(
                resource = R.raw.quiz,
                message = stringResource(id = R.string.no_flashcards_yet),
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.medium,
                    )
                    .align(Alignment.Center),
            ) {
                QuizType(
                    title = stringResource(R.string.knowledge_directions),
                    subtitle = stringResource(R.string.knowledge_directions_message),
                    level = stringResource(R.string.easy),
                    color = AndroidGreen,
                    onClick = openKnowledgeDirections,
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                )

                QuizType(
                    title = stringResource(R.string.matching),
                    subtitle = stringResource(R.string.matching_message),
                    level = stringResource(R.string.medium),
                    color = Color.Yellow,
                    onClick = openMatching,
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                )

                QuizType(
                    title = stringResource(R.string.typing),
                    subtitle = stringResource(R.string.typing_message),
                    level = stringResource(R.string.hard),
                    color = Color.Red,
                    onClick = openTyping,
                )
            }
        }
    }
}

@Composable
private fun QuizType(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    level: String,
    color: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(10.dp)
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .wrapContentHeight(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = level.uppercase(),
                style = MaterialTheme.typography.labelMedium,
            )

            Spacer(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(10.dp)
                    .drawBehind {
                        drawCircle(color)
                    },
            )
        }
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
        )
    }
}

@DevicePreviews
@LocalePreviews
@Composable
private fun QuizScreenPreview() {
    MemoratiTheme {
        Surface {
            QuizScreen(
                state = false,
                openTyping = {},
                openMatching = {},
                openKnowledgeDirections = {},
            )
        }
    }
}
