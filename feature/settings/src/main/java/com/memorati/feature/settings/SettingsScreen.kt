package com.memorati.feature.settings

import MemoratiIcons
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.icon.CompareArrows
import com.memorati.core.design.icon.Insights
import com.memorati.feature.settings.model.SettingsState

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state by viewModel.settings.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val title = stringResource(id = R.string.share_flashcards_message)
    SettingsScreen(
        modifier = modifier,
        state = state,
        onBack = onBack,
        onExport = {
            viewModel.exportFlashcards(title, context)
        },
        onImport = viewModel::importFile,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onBack: () -> Unit,
    onExport: () -> Unit,
    onImport: (Uri?) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent(),
    ) { uri ->
        onImport(uri)
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            modifier = Modifier.shadow(2.dp),
            title = {
                Text(text = stringResource(id = R.string.settings))
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = MemoratiIcons.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                    )
                }
            },
        )

        SettingsTile(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp),
            title = stringResource(id = R.string.insights),
            imageVector = MemoratiIcons.Insights,
        ) {
            Text(text = stringResource(id = R.string.flashcards_count, state.flashcardsCount))
        }

        SettingsTile(
            modifier = Modifier.padding(horizontal = 5.dp),
            title = stringResource(id = R.string.data_transfer),
            imageVector = MemoratiIcons.CompareArrows,
        ) {
            Button(
                onClick = onExport,
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    imageVector = MemoratiIcons.Export,
                    contentDescription = stringResource(id = R.string.export),
                )

                Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                Text(text = stringResource(id = R.string.export))
            }

            Button(
                onClick = {
                    launcher.launch("application/json")
                },
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    imageVector = MemoratiIcons.Import,
                    contentDescription = stringResource(id = R.string.import_text),
                )

                Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                Text(text = stringResource(id = R.string.import_text))
            }
        }
    }
}

@Composable
@Preview
internal fun SettingsScreenPreview(modifier: Modifier = Modifier) {
    SettingsScreen(
        state = SettingsState(flashcardsCount = 10),
        onBack = {},
        onExport = {},
        onImport = {},
    )
}
