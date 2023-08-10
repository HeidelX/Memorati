package com.memorati.feature.quiz.knowledge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun KnowledgeDirectionsRoute(
    modifier: Modifier = Modifier,
    viewModel: KnowledgeDirectionsViewModel = hiltViewModel(),
) {
    val flashcards by viewModel.state.collectAsStateWithLifecycle()
    KnowledgeDirectionsScreen(
        modifier = modifier,
        flashcards = flashcards,
        onSwipeCardLeft = viewModel::onSwipeCardLeft,
        onSwipeCardRight = viewModel::onSwipeCardRight,
    )
}
