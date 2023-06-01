package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.AssistantCard

@Composable
fun AssistantRoute(
    modifier: Modifier = Modifier,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val cards by viewModel.assistantCards.collectAsStateWithLifecycle()
    AssistantScreen(
        modifier = modifier,
        assistantCards = cards,
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    assistantCards: List<AssistantCard>,
) {
    if (assistantCards.isNotEmpty()) {
        AssistantList(assistantCards = assistantCards, modifier = modifier)
    } else {
        EmptyScreen(
            imageVector = MemoratiIcons.Assistant,
            message = stringResource(id = R.string.no_assistant_cards_message),
        )
    }
}
