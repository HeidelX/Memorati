package com.memorati.feature.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen


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
                message = stringResource(id = R.string.no_flashcards_yet)
            )
        } else {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Button(onClick = openKnowledgeDirections) {
                    Text(text = "Knowledge Directions")
                }

                Button(onClick = openMatching) {
                    Text(text = "Matching")
                }

                Button(onClick = openTyping) {
                    Text(text = "Typing")
                }
            }
        }
    }

}

@Composable
@Preview
private fun QuizScreenPreview() {
    QuizScreen(
        state = true,
        openTyping = {},
        openMatching = {},
        openKnowledgeDirections = {},
    )
}