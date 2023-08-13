package com.memorati.feature.quiz.typing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TypingRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    TypingScreen(
        modifier = modifier,
        onBack = onBack,
    )
}

@Composable
internal fun TypingScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
}
