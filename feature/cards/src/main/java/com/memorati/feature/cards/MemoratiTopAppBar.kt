package com.memorati.feature.cards

import com.memorati.core.design.icon.MemoratiIcons
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.theme.MemoratiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoratiTopAppBar(
    modifier: Modifier = Modifier,
    openSettings: () -> Unit,
    onQueryChange: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        query = text,
        onQueryChange = {
            text = it
            onQueryChange(text)
        },
        onSearch = { },
        active = false,
        onActiveChange = {},
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        leadingIcon = {
            Icon(
                MemoratiIcons.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            AppBarMenu { openSettings() }
        },
        content = {},
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.small,
    )
}

@Composable
private fun AppBarMenu(
    modifier: Modifier = Modifier,
    openSettings: () -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = MemoratiIcons.MoreVert,
            contentDescription = stringResource(id = R.string.more),
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.settings)) },
            leadingIcon = {
                Icon(MemoratiIcons.Settings, stringResource(id = R.string.settings))
            },
            onClick = {
                expanded = false
                openSettings()
            },
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.share)) },
            leadingIcon = {
                Icon(MemoratiIcons.Share, stringResource(id = R.string.share))
            },
            onClick = {
                expanded = false
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, GOOGLE_PLAY)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(
                    sendIntent,
                    context.getString(R.string.share_memorati),
                )
                context.startActivity(shareIntent)
            },
        )
    }
}

private const val GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=com.memorati"

@DevicePreviews
@Composable
fun MemoratiTopAppBarPreview() {
    MemoratiTheme {
        MemoratiTopAppBar(
            onQueryChange = {},
            openSettings = {},
        )
    }
}
