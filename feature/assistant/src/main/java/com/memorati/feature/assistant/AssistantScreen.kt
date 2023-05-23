package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.memorati.core.design.component.EmptyScreen

@Composable
fun AssistantRoute(
    modifier: Modifier = Modifier,
) {
    AssistantScreen()
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
) {
    EmptyScreen(
        imageVector = MemoratiIcons.Assistant,
        message = stringResource(id = R.string.no_assistant_cards_message),
    )
}
