package com.memorati.core.design.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun MemoratiChip(
    label: String,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
) {
    AssistChip(
        modifier = modifier.padding(2.dp),
        onClick = {},
        label = {
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = label.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
            )
        },
        shape = CircleShape,
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = if (disabled) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.12f,
                )
            } else {
                Color.Transparent
            },
            disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.38f,
            ),
        ),
    )
}

@Preview
@Composable
internal fun ChipPreview() {
    MemoratiChip("hello")
}