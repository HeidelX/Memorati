package com.memorati.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.MemoratiIcons
import com.memorati.core.model.UserData
import com.memorati.feature.settings.TimePickerRequest.END
import com.memorati.feature.settings.TimePickerRequest.START
import kotlinx.datetime.toDateTimePeriod

@Composable
internal fun NotificationsSettings(
    modifier: Modifier = Modifier,
    userData: UserData,
    timeRequest: (TimePickerRequest) -> Unit,
    showDurationPicker: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.padding(vertical = 10.dp),
    ) {
        SectionTile(
            imageVector = MemoratiIcons.Snooze,
            label = stringResource(R.string.quiet_time),
        )

        Spacer(modifier = Modifier.height(10.dp))

        TimeRow(
            label = stringResource(R.string.start_time),
            time = userData.startTime.toString(),
            onClick = { timeRequest(START) },
        )

        TimeRow(
            label = stringResource(R.string.end_time),
            time = userData.endTime.toString(),
            onClick = { timeRequest(END) },
        )

        Spacer(modifier = Modifier.height(10.dp))

        SectionTile(
            imageVector = MemoratiIcons.Timelapse,
            label = stringResource(R.string.reminder_interval),
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = stringResource(id = R.string.interval_description),
            style = MaterialTheme.typography.bodySmall,
        )

        TimeRow(
            label = stringResource(R.string.interval),
            time = stringResource(
                R.string.interval_format,
                userData.reminderInterval.toDateTimePeriod().hours,
                userData.reminderInterval.toDateTimePeriod().minutes,
            ),
            onClick = { showDurationPicker(true) },
        )
    }
}

@Composable
private fun SectionTile(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    label: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = label,
        )

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun TimeRow(
    modifier: Modifier = Modifier,
    label: String,
    time: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1.0f),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            modifier = Modifier.padding(end = 10.dp),
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
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
