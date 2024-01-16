package com.memorati.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CardsStack(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    items: List<T>,
    onSwipeCardEnd: (T) -> Boolean,
    onSwipeCardStart: (T) -> Boolean,
    itemKey: (T) -> Any,
    cardContent: @Composable (T) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ) {
        items.forEachIndexed { order, item ->
            key(itemKey(item)) {
                SwipeableCard(
                    order = order,
                    count = items.size,
                    onSwipeStart = { onSwipeCardStart(item) },
                    onSwipeEnd = { onSwipeCardEnd(item) },
                ) {
                    cardContent(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CardsStackIndexed(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    items: List<T>,
    onSwipeCardEnd: (T) -> Boolean,
    onSwipeCardStart: (T) -> Boolean,
    itemKey: (T) -> Any,
    itemSwipe: (T) -> SwipeToDismissBoxValue = { SwipeToDismissBoxValue.Settled },
    cardContent: @Composable (Int, T) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ) {
        items.forEachIndexed { order, item ->
            key(itemKey(item)) {
                SwipeableCard(
                    order = order,
                    count = items.size,
                    onSwipeStart = { onSwipeCardStart(item) },
                    onSwipeEnd = { onSwipeCardEnd(item) },
                    initialValue = itemSwipe(item),
                ) {
                    cardContent(order, item)
                }
            }
        }
    }
}

@Preview
@Composable
private fun CardsStackPreview() {
    CardsStack(
        modifier = Modifier.fillMaxSize(),
        items = listOf("A", "B", "C", "D"),
        onSwipeCardEnd = { false },
        onSwipeCardStart = { false },
        itemKey = {},
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        ) {
            // Content
        }
    }
}
