package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.AssistantCard
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.AssistantState
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewResult

@Composable
fun AssistantRoute(
    modifier: Modifier = Modifier,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AssistantScreen(
        modifier = modifier,
        state = state,
        onOptionSelected = viewModel::selectOption,
        onUpdateCard = viewModel::updateCard,
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    state: AssistantState,
    onOptionSelected: (AssistantCard, String) -> Unit,
    onUpdateCard: (AssistantCard, Boolean) -> Unit,
) {
    when (state) {
        is AssistantCards -> AssistantPager(
            assistantCards = state.reviews,
            modifier = modifier,
            onOptionSelected = onOptionSelected,
            onUpdateCard = onUpdateCard,
        )

        is ReviewResult -> ReviewResultScreen(reviewResult = state)

        EmptyState -> EmptyScreen(
            imageVector = MemoratiIcons.AutoAwesome,
            message = stringResource(id = R.string.no_assistant_cards_message),
        )
    }
}

@Composable
@Preview
internal fun AssistantScreenEmptyPreview() {
    AssistantScreen(
        state = EmptyState,
        onOptionSelected = { _, _ -> },
        onUpdateCard = { _, _ -> },
    )
}
