package com.memorati.feature.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.MemoratiIcons

@Composable
internal fun ClearAppDataDialog(onDismiss: () -> Unit, onClear: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(id = R.string.clear_dialog_title))
        },
        text = {
            Text(stringResource(id = R.string.clear_dialog_message))
        },
        confirmButton = {
            Button(
                onClick = {
                    onClear()
                    onDismiss()
                },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp,
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                ),
            ) {
                Icon(
                    MemoratiIcons.Delete,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.clear))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
            ) {
                Icon(
                    MemoratiIcons.Close,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                Text(stringResource(id = R.string.cancel))
            }
        },
    )
}

@Preview
@Composable
fun ClearAppDataDialogPreview() {
    ClearAppDataDialog(
        onDismiss = { },
        onClear = {},
    )
}
