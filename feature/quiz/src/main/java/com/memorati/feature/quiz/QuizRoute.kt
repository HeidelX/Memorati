package com.memorati.feature.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun QuizRoute(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    val flashcards by viewModel.state.collectAsStateWithLifecycle()
    QuizScreen(
        modifier = modifier,
        flashcards = flashcards,
        onSwipeCardLeft = viewModel::onSwipeCardLeft,
        onSwipeCardRight = viewModel::onSwipeCardRight,
    )
}
