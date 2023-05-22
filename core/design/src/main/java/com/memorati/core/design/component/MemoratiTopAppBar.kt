package com.memorati.core.design.component

import MemoratiIcons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.memorati.core.design.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoratiTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = MemoratiIcons.Search,
                    contentDescription = stringResource(R.string.app_name),
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = MemoratiIcons.Settings,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MemoratiTopAppBarPreview() {
    MemoratiTopAppBar()
}
