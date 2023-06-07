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
import com.memorati.feature.assistant.state.AssistantState

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
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    state: AssistantState,
    onOptionSelected: (Long, String) -> Unit,

) {
    if (state.reviews.isNotEmpty()) {
        AssistantPager(
            assistantCards = state.reviews,
            modifier = modifier,
            onOptionSelected = onOptionSelected,
        )
    } else {
        EmptyScreen(
            imageVector = MemoratiIcons.AutoAwesome,
            message = stringResource(id = R.string.no_assistant_cards_message),
        )
    }
}

@Composable
@Preview
internal fun AssistantScreenEmptyPreview() {
    AssistantScreen(
        state = AssistantState(),
    ) { _, _ -> }
}
