package com.memorati.feature.assistant

import MemoratiIcons
import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.memorati.core.common.permission.openNotificationsSettings
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.model.AssistantCard
import com.memorati.feature.assistant.state.AssistantCards
import com.memorati.feature.assistant.state.AssistantState
import com.memorati.feature.assistant.state.EmptyState
import com.memorati.feature.assistant.state.ReviewResult

@Composable
fun AssistantRoute(
    modifier: Modifier = Modifier,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AssistantScreen(
        modifier = modifier,
        state = state,
        onOptionSelected = viewModel::selectOption,
        onUpdateCard = viewModel::updateCard,
    )
}

@Composable
internal fun AssistantScreen(
    modifier: Modifier = Modifier,
    state: AssistantState,
    onOptionSelected: (AssistantCard, String) -> Unit,
    onUpdateCard: (AssistantCard, Boolean) -> Unit,
) {
    Column {
        Box(modifier = Modifier.weight(1.0f)) {
            when (state) {
                is AssistantCards -> AssistantPager(
                    assistantCards = state.reviews,
                    modifier = modifier,
                    onOptionSelected = onOptionSelected,
                    onUpdateCard = onUpdateCard,
                )

                is ReviewResult -> ReviewResultScreen(reviewResult = state)

                EmptyState -> EmptyScreen(
                    imageVector = MemoratiIcons.AutoAwesome,
                    message = stringResource(id = R.string.no_assistant_cards_message),
                )
            }
        }

        PostNotificationPermission()
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun PostNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        val permissionState = rememberPermissionState(POST_NOTIFICATIONS)
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
                        if (!permissionState.status.shouldShowRationale) {
                            permissionState.launchPermissionRequest()
                        } else {
                            context.openNotificationsSettings()
                        }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.grant),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
internal fun AssistantScreenEmptyPreview() {
    AssistantScreen(
        state = EmptyState,
        onOptionSelected = { _, _ -> },
        onUpdateCard = { _, _ -> },
    )
}
