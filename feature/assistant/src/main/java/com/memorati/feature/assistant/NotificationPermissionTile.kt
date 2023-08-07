package com.memorati.feature.assistant

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.memorati.core.common.permission.openNotificationsSettings

@Composable
@OptIn(ExperimentalPermissionsApi::class)
internal fun NotificationPermissionTile() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        if (!permissionState.status.isGranted) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = stringResource(R.string.notification_permission),
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(modifier = Modifier.width(5.dp))

                OutlinedButton(
                    onClick = {
                        context.openNotificationsSettings()
                    },
                ) {
                    Text(
                        text = stringResource(R.string.grant),
                    )
                }
            }

            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}