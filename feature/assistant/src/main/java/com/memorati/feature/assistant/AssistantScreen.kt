package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.feature.assistant.model.AssistantCard

@Composable
fun AssistantRoute(
    modifier: Modifier = Modifier,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val cards by viewModel.flashcards.collectAsStateWithLifecycle()
    AssistantScreen(
        modifier = modifier,
        flashcards = cards,
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    flashcards: List<AssistantCard>,
) {
    if (flashcards.isNotEmpty()) {
        AssistantList(flashcards = flashcards, modifier = modifier)
    } else {
        EmptyScreen(
            imageVector = MemoratiIcons.Assistant,
            message = stringResource(id = R.string.no_assistant_cards_message),
        )
    }
}
