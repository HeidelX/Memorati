package com.memorati.feature.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun CardsList(
    state: CardsState,
    modifier: Modifier = Modifier,
    toggleFavoured: (Flashcard) -> Unit = {},
    onDelete: (Flashcard) -> Unit = {},
    onEdit: (Flashcard) -> Unit = {},
    onAddCard: () -> Unit = {},
) {
    val lazyListState = rememberLazyListState()
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp,
            ),
        ) {
            state.map.forEach { (date, cards) ->
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(all = 8.dp),
                        text = date.toString(),
                    )
                }
                items(cards, key = { it.id }) { card ->
                    CardItem(
                        modifier = Modifier.animateItemPlacement(),
                        card = card,
                        toggleFavoured = toggleFavoured,
                        onDelete = onDelete,
                        onEdit = onEdit,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }

            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
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
        }
    )
}


@DevicePreviews
@Composable
internal fun CardsScreenPreview() {
    CardsScreen(
        state = CardsState(
            mapOf(
                LocalDate(2023, 12, 10) to listOf(
                    Flashcard(
                        id = 1,
                        front = "Hello",
                        back = "Hallo",
                        createdAt = Clock.System.now(),
                    ),
                    Flashcard(
                        id = 2,
                        front = "Hello",
                        back = "Hallo",
                        createdAt = Clock.System.now(),
                        favoured = true,
                    ),
                    Flashcard(
                        id = 3,
                        front = "Hello",
                        back = "Hallo",
                        createdAt = Clock.System.now(),
                    ),
                ),
            ),
        ),
        toggleFavoured = {},
        onEdit = {},
        onDelete = {},
        onAddCard = {},
    )
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
