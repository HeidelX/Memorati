package com.memorati.feature.settings

import MemoratiIcons
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.launch

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
        onExport = {
            viewModel.exportFlashcards(title, context)
        },
        onImport = viewModel::importFile,
        onClear = viewModel::clearData,
        appVersion = appVersion,
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
    onClear: () -> Unit,
    appVersion: String,
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent(),
    ) { uri ->
        onImport(uri)
    }

    var notificationEnabled by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showClearDialog by remember { mutableStateOf(false) }
    val pickerState = rememberTimePickerState()
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    if (state.error != null) {
        LaunchedEffect(state.error) {
            snackScope.launch {
                snackState.showSnackbar(
                    message = state.error.localizedMessage ?: "Unknown error occurred",
                    withDismissAction = true,
                    duration = SnackbarDuration.Long,
                )
            }
        }
    }

    Box(propagateMinConstraints = false) {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()),
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
                title = stringResource(id = R.string.notifications),
                imageVector = MemoratiIcons.Notifications,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .clickable {
                            notificationEnabled = !notificationEnabled
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = stringResource(id = R.string.allow_notifications),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Switch(
                        modifier = Modifier.padding(start = 10.dp),
                        checked = notificationEnabled,
                        onCheckedChange = {
                            notificationEnabled = it
                        },
                    )
                }

                AnimatedVisibility(visible = notificationEnabled) {
                    Column(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(5.dp),
                            )
                            .padding(10.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = MemoratiIcons.Snooze,
                                contentDescription = stringResource(R.string.quiet_time),
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = stringResource(R.string.quiet_time),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(R.string.start_time),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.weight(1.0f))
                            Text(
                                text = "17:00",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(R.string.end_time),
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            Spacer(modifier = Modifier.weight(1.0f))

                            Text(
                                text = "09:00",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
                if (showTimePicker) {
                    TimePickerDialog(
                        onCancel = { showTimePicker = false },
                        onConfirm = {
                            showTimePicker = false
                        },
                    ) {
                        TimePicker(state = pickerState)
                    }
                }
            }

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
                Row {
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
                    Spacer(modifier = Modifier.weight(1.0f))
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

            SettingsTile(
                modifier = Modifier.padding(horizontal = 5.dp),
                title = stringResource(id = R.string.app_data),
                imageVector = MemoratiIcons.Storage,
            ) {
                Button(
                    onClick = {
                        showClearDialog = true
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        imageVector = MemoratiIcons.Delete,
                        contentDescription = stringResource(id = R.string.clear),
                    )

                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                    Text(text = stringResource(id = R.string.clear))
                }
            }

            if (showClearDialog) {
                AlertDialog(
                    onDismissRequest = { showClearDialog = false },
                    title = {
                        Text(stringResource(id = R.string.clear_dialog_title))
                    },
                    text = {
                        Text(stringResource(id = R.string.clear_dialog_message))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                onClear()
                                showClearDialog = false
                            },
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                top = 12.dp,
                                end = 20.dp,
                                bottom = 12.dp,
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                            ),
                        ) {
                            Icon(
                                MemoratiIcons.Delete,
                                contentDescription = stringResource(id = R.string.clear),
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(stringResource(id = R.string.clear))
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showClearDialog = false },
                        ) {
                            Icon(
                                MemoratiIcons.Close,
                                contentDescription = stringResource(id = R.string.clear),
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                            Text(stringResource(id = R.string.cancel))
                        }
                    },
                )
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = "App Version $appVersion",
                style = MaterialTheme.typography.labelSmall,
            )
        }
        SnackbarHost(
            hostState = snackState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
@Preview
internal fun SettingsScreenPreview() {
    SettingsScreen(
        state = SettingsState(flashcardsCount = 10),
        onBack = {},
        onExport = {},
        onImport = {},
        onClear = {},
        appVersion = "1.0.0.2",
    )
}
