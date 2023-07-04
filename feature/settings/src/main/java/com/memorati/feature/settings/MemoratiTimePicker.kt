package com.memorati.feature.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.LocalTime

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun MemoratiTimePicker(
    initialTime: LocalTime,
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
) {
    val pickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true,
    )
    TimePickerDialog(
        onCancel = onDismiss,
        onConfirm = {
            onTimeSelected(
                pickerState.hour,
                pickerState.minute,
            )
            onDismiss()
        },
    ) {
        androidx.compose.material3.TimePicker(state = pickerState)
    }
}

@Preview
@Composable
fun MemoratiTimePickerPreview() {
    MemoratiTimePicker(
        initialTime = LocalTime(10, 34),
        onDismiss = { },
        onTimeSelected = { _, _ -> },
    )
}
