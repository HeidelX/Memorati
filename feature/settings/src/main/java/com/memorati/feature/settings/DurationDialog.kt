package com.memorati.feature.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.toDateTimePeriod
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DurationPicker(
    duration: Duration,
    onDurationSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val intervalPeriod = duration.toDateTimePeriod()
    val durationHours = intervalPeriod.hours
    val durationMinutes = intervalPeriod.minutes
    val durationState = rememberTimePickerState(
        initialHour = durationHours,
        initialMinute = durationMinutes,
        is24Hour = true,
    )
    TimePickerDialog(
        title = stringResource(id = R.string.select_duration),
        onCancel = onDismiss,
        onConfirm = {
            onDurationSelected(
                durationState.hour,
                durationState.minute,
            )
            onDismiss()
        },
    ) {
        TimePicker(state = durationState)
    }
}

@Preview
@Composable
fun DurationDialogPreview() {
    DurationPicker(
        duration = 10.hours.plus(30.minutes),
        onDurationSelected = { _, _ -> },
        onDismiss = {},
    )
}
