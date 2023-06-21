package com.memorati.core.design.component

import MemoratiIcons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.memorati.core.design.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoratiTopAppBar(modifier: Modifier = Modifier, onQueryChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    Box(
        modifier
            .semantics { isTraversalGroup = true }
            .zIndex(1f)
            .fillMaxWidth(),
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
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
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = MemoratiIcons.Settings,
                        contentDescription = stringResource(id = R.string.settings),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
            content = {},
        )
    }
}

@Preview
@Composable
fun MemoratiTopAppBarPreview() {
    MemoratiTopAppBar {}
}
