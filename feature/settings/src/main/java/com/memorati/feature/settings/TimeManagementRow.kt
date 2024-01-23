package com.memorati.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.MemoratiIcons
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun TimeManagementRow(
    modifier: Modifier = Modifier,
    count: Int,
    label: String,
    onValueChange: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )

        TextField(
            modifier = Modifier.width(width = 200.dp),
            value = count.toString(),
            onValueChange = {},
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            readOnly = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onValueChange(if (count > 1) count - 1 else count)
                    },
                    enabled = count > 1
                ) {
                    Icon(imageVector = MemoratiIcons.Minus, contentDescription = "decrease")
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onValueChange(if (count < 1000) count + 1 else count)
                    },
                    enabled = count < 1000,
                ) {
                    Icon(imageVector = MemoratiIcons.Add, contentDescription = "increase")
                }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
    }
}

@Composable
@DevicePreviews
@LocalePreviews
internal fun TimeManagementRowPreview() {
    MemoratiTheme {
        TimeManagementRow(
            count = 1,
            label = "Settings",
            onValueChange = {},
        )
    }
}