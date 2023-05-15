package com.memorati.feature.cards.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.memorati.core.ui.DevicePreviews

@Composable
fun CardCreationRoute(
    modifier: Modifier = Modifier,
    viewModel: CardCreationViewModel = hiltViewModel(),
) {
    CardCreationScreen(modifier = modifier) { front, back ->
        viewModel.createCard(front, back)
    }
}

@Composable
internal fun CardCreationScreen(
    modifier: Modifier = Modifier,
    onCreate: (String, String) -> Unit,
) {
    var front by remember { mutableStateOf("") }
    var back by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        TextField(value = front, onValueChange = { text ->
            front = text
        })
        TextField(value = back, onValueChange = { text ->
            back = text
        })
        Button(
            modifier = modifier.align(Alignment.End),
            onClick = {
                onCreate(front, back)
            },
        ) {
            Text(text = "Create")
        }
    }
}

@DevicePreviews
@Composable
internal fun CardCreationRoutePreview() {
    CardCreationScreen() { _, _ -> }
}
