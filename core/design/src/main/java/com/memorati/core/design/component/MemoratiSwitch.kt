package com.memorati.core.design.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun MemoratiSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable {
                onChecked(!checked)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )

        Switch(
            modifier = Modifier.padding(start = 10.dp),
            checked = checked,
            onCheckedChange = onChecked,
        )
    }
}
