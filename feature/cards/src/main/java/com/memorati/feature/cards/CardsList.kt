package com.memorati.feature.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.design.component.EmptyScreen
import com.memorati.core.design.component.MemoratiTopAppBar
import com.memorati.core.design.icon.CardMembership
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.ext.isScrollingUp
import com.memorati.core.ui.provider.FlashcardsProvider
import kotlinx.datetime.LocalDate

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun CardsScreen(
    state: CardsState,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit = {},
    onDelete: (Flashcard) -> Unit = {},
    onEdit: (Flashcard) -> Unit = {},
    onAddCard: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    openSettings: () -> Unit = {},
    speak: (String) -> Unit = {},
) {
    val lazyListState = rememberLazyListState()
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            MemoratiTopAppBar(
                onQueryChange = onQueryChange,
                openSettings = openSettings,
            )
            AnimatedVisibility(
                visible = state.map.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 16.dp,
                    ),
                ) {
                    state.map.forEach { (date, cards) ->
                        stickyHeader(key = date.toString()) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemPlacement()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(all = 8.dp),
                                text = date.toString(),
                                fontFamily = FontFamily.Monospace,
                            )
                        }
                        items(cards, key = { it.id }) { card ->
                            CardItem(
                                modifier = Modifier.animateItemPlacement(),
                                card = card,
                                state = state,
                                toggleFavoured = toggleFavoured,
                                onDelete = onDelete,
                                onEdit = onEdit,
                                speak = speak,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = state.map.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            EmptyScreen(
                imageVector = MemoratiIcons.CardMembership,
                message = stringResource(id = R.string.no_cards_message),
            )
        }

        FabButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            expanded = lazyListState.isScrollingUp(),
            onClickAction = onAddCard,
        )
    }
}

@Composable
internal fun FabButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    expanded: Boolean,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        expanded = expanded,
        onClick = { onClickAction() },
        icon = {
            Icon(
                MemoratiIcons.Add,
                contentDescription = stringResource(id = R.string.add),
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
        },
        text = {
            Text(text = stringResource(id = R.string.add))
        },
    )
}

@DevicePreviews
@Composable
internal fun CardsScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    CardsScreen(
        state = CardsState(
            map = mapOf(
                LocalDate(2023, 12, 10) to flashcards,
            ),
            query = "omm",
            isSpeechEnabled = true,
        ),
        toggleFavoured = {},
        onEdit = {},
        onDelete = {},
        onAddCard = {},
        onQueryChange = {},
        openSettings = {},
        speak = {},
    )
}
