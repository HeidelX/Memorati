package com.memorati.feature.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun CardCreationRoute(
    modifier: Modifier = Modifier,
    viewModel: CardCreationViewModel = hiltViewModel(),
    onCardCreated: () -> Unit,
) {
    CardCreationScreen(modifier = modifier) { front, back ->
        viewModel.createCard(front, back)
        onCardCreated()
    }
}

@Composable
internal fun CardCreationScreen(
    modifier: Modifier = Modifier,
    onCreate: (String, String) -> Unit,
) {
    var idiom by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = idiom,
                label = {
                    Text(text = "Idiom")
                },
                onValueChange = { text ->
                    idiom = text
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = description,
                label = {
                    Text(text = "Description")
                },
                onValueChange = { text ->
                    description = text
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                enabled = idiom.isNotBlank() && description.isNotBlank(),
                modifier = modifier.align(Alignment.End),
                onClick = {
                    onCreate(idiom, description)
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Save,
                    contentDescription = stringResource(id = R.string.save)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                Text(text = stringResource(id = R.string.save))

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}

@Preview
@Composable
internal fun CardCreationRoutePreview() {
    CardCreationScreen { _, _ -> }
}
