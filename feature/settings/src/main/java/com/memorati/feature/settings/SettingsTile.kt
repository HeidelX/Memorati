package com.memorati.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.MemoratiIcons

@Composable
internal fun SettingsTile(
    modifier: Modifier = Modifier,
    title: String,
    imageVector: ImageVector,
    visible: Boolean = true,
    contentPadding: Dp = 24.dp,
    content: @Composable () -> Unit,
) {
    if (visible) {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    text = title,
                )

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = imageVector,
                    contentDescription = title,
                )
            }

            Column(modifier = Modifier.padding(all = contentPadding)) {
                content()
            }
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
