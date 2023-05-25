package com.memorati.feature.cards

import MemoratiIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import kotlinx.datetime.Clock
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CardItem(
    card: Flashcard,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit,
    onDelete: (Flashcard) -> Unit,
    onEdit: (Flashcard) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        Surface(
            modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(10.dp),
            color = MaterialTheme.colorScheme.primary,
        ) {
            Box(modifier = modifier) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize(),
                ) {
                    Text(
                        text = card.front,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                    )

                    Divider(
                        modifier = modifier
                            .width(150.dp)
                            .padding(vertical = 10.dp),
                    )
                    Text(
                        text = card.back,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                FilledIconToggleButton(
                    modifier = modifier.align(Alignment.BottomEnd),
                    checked = card.favoured,
                    onCheckedChange = {
                        toggleFavoured(card)
                    },
                    colors = IconButtonDefaults.iconToggleButtonColors(
                        checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = if (card.favoured) {
                            MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.12f,
                            )
                        } else {
                            Color.Transparent
                        },
                    ),
                ) {
                    Icon(
                        imageVector = if (card.favoured) {
                            MemoratiIcons.Favourites
                        } else {
                            MemoratiIcons.FavouritesBorder
                        },
                        contentDescription = if (card.favoured) {
                            stringResource(id = R.string.remove_from_favourites)
                        } else {
                            stringResource(id = R.string.add_to_favourites)
                        },
                    )
                }

                CardDropDownMenu(
                    modifier = modifier.align(Alignment.TopEnd),
                    onDelete = { onDelete(card) },
                    onEdit = { onEdit(card) },
                )

                FlowRow(
                    modifier = modifier.align(Alignment.BottomStart),
                ) {
                    card.topics.forEach { topic ->
                        MemoratiChip(topic.label)
                    }
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

@Composable
internal fun MemoratiChip(
    label: String,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
) {
    AssistChip(
        modifier = modifier.padding(2.dp),
        onClick = {},
        label = {
            Text(
                text = label.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
            )
        },
        shape = CircleShape,
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = if (disabled) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.12f,
                )
            } else {
                Color.Transparent
            },
            disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.38f,
            ),
        ),
    )
}

@Preview
@Composable
internal fun CardItemPreview() {
    CardItem(
        card = Flashcard(
            id = 1,
            front = "Hello",
            back = "Hallo",
            createdAt = Clock.System.now(),
            topics = listOf(Topic(0, "de"), Topic(0, "A1"), Topic(0, "A2")),
        ),
        toggleFavoured = {},
        onEdit = {},
        onDelete = {},
    )
}

@Preview
@Composable
internal fun ChipPreview() {
    MemoratiChip("hello")
}