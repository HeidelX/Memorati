package com.memorati.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    appVersion: String,
) {
    val state by viewModel.settings.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val title = stringResource(id = R.string.share_flashcards_message)
    SettingsScreen(
        modifier = modifier,
        state = state,
        onBack = onBack,
        appVersion = appVersion,
        onClear = viewModel::clearData,
        onImport = viewModel::importFile,
        onTimeSelected = viewModel::onTimeSelected,
        onDurationSelected = viewModel::onDurationSelected,
        onExport = { viewModel.exportFlashcards(title, context) },
    )
}
