package com.memorati.feature.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.memorati.core.common.permission.openNotificationsSettings
import com.memorati.core.design.component.MemoratiSwitch
import com.memorati.core.design.icon.MemoratiIcons
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.settings.TimePickerRequest.DISMISS
import com.memorati.feature.settings.TimePickerRequest.START
import com.memorati.feature.settings.chart.Chart
import com.memorati.feature.settings.chart.dayEntries
import com.memorati.feature.settings.model.SettingsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    appVersion: String,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onExport: () -> Unit,
    onImport: (Uri?) -> Unit,
    onDurationSelected: (Int, Int) -> Unit,
    onTimeSelected: (TimePickerRequest, Int, Int) -> Unit,
) {
    val context = LocalContext.current
    val snackScope = rememberCoroutineScope()
    val snackState = remember { SnackbarHostState() }
    var showTimePicker by remember { mutableStateOf(DISMISS) }
    var showClearDialog by remember { mutableStateOf(false) }
    var showDurationPicker by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(GetContent()) { uri -> onImport(uri) }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
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

            val userData = state.userData

            SettingsTile(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp),
                title = stringResource(id = R.string.notifications),
                imageVector = MemoratiIcons.Notifications,
            ) {
                MemoratiSwitch(
                    checked = state.notificationsEnabled,
                    text = stringResource(id = R.string.allow_notifications),
                    onChecked = {
                        context.openNotificationsSettings()
                    },
                )

                AnimatedVisibility(visible = state.notificationsEnabled) {
                    NotificationsSettings(
                        userData = userData,
                        timeRequest = { showTimePicker = it },
                        showDurationPicker = { showDurationPicker = it },
                    )
                }
                if (showTimePicker != DISMISS) {
                    MemoratiTimePicker(
                        initialTime = if (showTimePicker == START) userData.startTime else userData.endTime,
                        onTimeSelected = { h, m -> onTimeSelected(showTimePicker, h, m) },
                        onDismiss = { showTimePicker = DISMISS },
                    )
                }
            }

            SettingsTile(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp),
                title = stringResource(R.string.manage_review_interval),
                imageVector = MemoratiIcons.Cup,
                contentPadding = 0.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Word correctness count:",
                    )
                    TextField(
                        modifier = Modifier.width(100.dp),
                        value = userData.wordCorrectnessCount.toString(),
                        onValueChange = {},
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Number of weeks to review:",
                    )
                    TextField(
                        modifier = Modifier.width(100.dp),
                        shape = MaterialTheme.shapes.medium,
                        value = userData.weeksOfReview.toString(),
                        onValueChange = {},
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }

            SettingsTile(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp),
                title = stringResource(id = R.string.insights),
                imageVector = MemoratiIcons.Insights,
                contentPadding = 0.dp,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(
                        id = R.string.flashcards_count,
                        state.flashcardsCount,
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )

                Chart(entries = state.chartEntries)
            }

            SettingsTile(
                modifier = Modifier.padding(horizontal = 5.dp),
                title = stringResource(id = R.string.data_transfer),
                imageVector = MemoratiIcons.CompareArrows,
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.Start,
                    ),
                    modifier = Modifier.fillMaxWidth(),
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
                ClearAppDataDialog(
                    onDismiss = {
                        showClearDialog = false
                    },
                    onClear = onClear,
                )
            }

            if (showDurationPicker) {
                DurationPicker(
                    duration = userData.reminderInterval,
                    onDismiss = { showDurationPicker = false },
                    onDurationSelected = onDurationSelected,
                )
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            LinksPanel()

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.app_version, appVersion),
                style = MaterialTheme.typography.labelSmall,
            )
        }

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

        SnackbarHost(
            hostState = snackState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
@DevicePreviews
@LocalePreviews
internal fun SettingsScreenPreview() {
    MemoratiTheme {
        Surface {
            SettingsScreen(
                appVersion = "1.0.0.2",
                state = SettingsState(
                    flashcardsCount = 10,
                    chartEntries = dayEntries(),
                ),
                onBack = {},
                onClear = {},
                onImport = {},
                onExport = {},
                onTimeSelected = { _, _, _ -> },
                onDurationSelected = { _, _ -> },
            )
        }
    }
}

enum class TimePickerRequest {
    START,
    END,
    DISMISS,
}
