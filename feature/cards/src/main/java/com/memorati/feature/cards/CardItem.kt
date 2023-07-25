package com.memorati.feature.cards

import MemoratiIcons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.FavouriteButton
import com.memorati.core.design.text.formAnnotatedString
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.theme.MemoratiTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CardItem(
    modifier: Modifier = Modifier,
    card: Flashcard,
    state: CardsState,
    speak: (Flashcard) -> Unit,
    onEdit: (Flashcard) -> Unit,
    onDelete: (Flashcard) -> Unit,
    toggleFavoured: (Flashcard) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .defaultMinSize(minHeight = 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = formAnnotatedString(state.query, card.idiom),
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 3,
                )

                Divider(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(vertical = 10.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                )
                Text(
                    text = formAnnotatedString(state.query, card.meaning),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                )
            }

            if (card.idiomLanguageTag != null) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = { speak(card) },
                ) {
                    Icon(
                        imageVector = MemoratiIcons.Speak,
                        contentDescription = stringResource(R.string.speak),
                    )
                }
            }

            FavouriteButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                favoured = card.favoured,
                onCheckedChange = {
                    toggleFavoured(card)
                },
            )

            CardDropDownMenu(
                modifier = Modifier.align(Alignment.TopEnd),
                onDelete = { onDelete(card) },
                onEdit = { onEdit(card) },
            )

            FlowRow(
                modifier = Modifier.align(Alignment.BottomStart),
            ) {
                card.topics.forEach { topic ->
                    (topic.label)
                }
            }
        }
    }
}

@Composable
internal fun CardDropDownMenu(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
    ) {
        IconButton(
            onClick = { expanded = true },
        ) {
            Icon(
                imageVector = MemoratiIcons.MoreVert,
                contentDescription = stringResource(R.string.more),
            )
        }
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.edit)) },
                leadingIcon = {
                    Icon(MemoratiIcons.Edit, stringResource(id = R.string.edit))
                },
                onClick = {
                    onEdit()
                    expanded = false
                },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.delete)) },
                leadingIcon = {
                    Icon(MemoratiIcons.Delete, stringResource(id = R.string.delete))
                },
                onClick = {
                    onDelete()
                    expanded = false
                },
            )
        }
    }
}

@DevicePreviews
@Composable
internal fun CardItemPreview(
    @PreviewParameter(FlashcardProvider::class) flashcard: Flashcard,
) {
    MemoratiTheme {
        CardItem(
            card = flashcard,
            toggleFavoured = {},
            onEdit = {},
            onDelete = {},
            state = CardsState(
                query = "ommu",
            ),
            speak = {},
        )
    }
}
