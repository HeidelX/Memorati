package com.memorati.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.core.model.UserData
import kotlinx.datetime.toDateTimePeriod

@Composable
internal fun NotificationsSettings(
    userData: UserData,
    timeRequest: (TimePickerRequest) -> Unit,
    showDurationPicker: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.padding(10.dp),
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
                .height(48.dp)
                .clickable { timeRequest(TimePickerRequest.START) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.start_time),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = userData.startTime.toString(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Row(
            modifier = Modifier
                .height(48.dp)
                .clickable { timeRequest(TimePickerRequest.END) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.end_time),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Text(
                text = userData.endTime.toString(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = MemoratiIcons.Timelapse,
                contentDescription = stringResource(R.string.reminder_interval),
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(R.string.reminder_interval),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.interval_description),
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .height(48.dp)
                .clickable { showDurationPicker(true) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.interval),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Text(
                text = stringResource(
                    R.string.interval_format,
                    userData.reminderInterval.toDateTimePeriod().hours,
                    userData.reminderInterval.toDateTimePeriod().minutes,
                ),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
internal fun NotificationsSettingsPreview() {
    NotificationsSettings(
        userData = UserData(),
        timeRequest = {},
        showDurationPicker = {},
    )
}
