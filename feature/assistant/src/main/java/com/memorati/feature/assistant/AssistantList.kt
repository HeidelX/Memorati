package com.memorati.feature.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.AssistantCard
import com.memorati.core.ui.provider.AssistantCardProvider

@Composable
fun AssistantList(modifier: Modifier = Modifier, assistantCards: List<AssistantCard>) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),
    ) {
        items(assistantCards) { flashcard ->
            AssistantItem(
                assistantCard = flashcard,
                onOptionSelected = {},
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun AssistantItem(
    modifier: Modifier = Modifier,
    assistantCard: AssistantCard,
    onOptionSelected: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .wrapContentHeight(),
    ) {
        Surface(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(24.dp)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
        ) {
            var selected by remember { mutableStateOf("") }
            Column {
                Text(
                    text = assistantCard.flashcard.front,
                    style = MaterialTheme.typography.titleMedium,
                )
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 10.dp),
                )

                assistantCard.answers.forEach { answer ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selected == answer,
                                onClick = {
                                    selected = answer
                                    onOptionSelected(answer)
                                },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = selected == answer,
                            onClick = {
                                selected = answer
                                onOptionSelected(answer)
                            },
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colorScheme.onPrimary,
                                selectedColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            ),

                        )
                        Text(
                            text = answer,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AssistantItemPreview(
    @PreviewParameter(AssistantCardProvider::class) assistantCard: AssistantCard,
) {
    AssistantItem(
        assistantCard = assistantCard,
        onOptionSelected = {},
    )
}
