package com.memorati.feature.cards.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.memorati.core.ui.DevicePreviews

@Composable
fun CardCreationRoute(
    modifier: Modifier = Modifier,
    viewModel: CardCreationViewModel = hiltViewModel(),
    onCreate: () -> Unit,
) {
    CardCreationScreen(modifier = modifier) { front, back ->
        viewModel.createCard(front, back)
        onCreate()
    }
}

@Composable
internal fun CardCreationScreen(
    modifier: Modifier = Modifier,
    onCreate: (String, String) -> Unit,
) {
    var front by remember { mutableStateOf("") }
    var back by remember { mutableStateOf("") }

    Surface {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = front,
                label = {
                    Text(text = "Idiom")
                },
                onValueChange = { text ->
                    front = text
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = back,
                label = {
                    Text(text = "Description")
                },
                onValueChange = { text ->
                    back = text
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
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
}

@DevicePreviews
@Composable
internal fun CardCreationRoutePreview() {
    CardCreationScreen { _, _ -> }
}
