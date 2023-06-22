package com.memorati.feature.settings

import MemoratiIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingsTile(
    modifier: Modifier = Modifier,
    title: String,
    imageVector: ImageVector,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                .padding(horizontal = 24.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.weight(1f),
                text = title,
            )

            Icon(
                tint = MaterialTheme.colorScheme.onSecondary,
                imageVector = imageVector,
                contentDescription = title,
            )
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            content()
        }
    }
}

@Composable
@Preview
internal fun SettingsTilePreview() {
    SettingsTile(
        title = "Insights",
        imageVector = MemoratiIcons.Done,
        content = {},
    )
}
