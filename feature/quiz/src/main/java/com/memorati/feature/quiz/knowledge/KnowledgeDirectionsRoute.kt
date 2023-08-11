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
    val knowledgeCards by viewModel.state.collectAsStateWithLifecycle()
    KnowledgeDirectionsScreen(
        modifier = modifier,
        knowledgeCards = knowledgeCards,
        onFlip = viewModel::onFlip,
        toggleFavoured = viewModel::toggleFavoured,
        onSwipeCardEnd = viewModel::onSwipeCardEnd,
        onSwipeCardStart = viewModel::onSwipeCardStart,
    )
}
