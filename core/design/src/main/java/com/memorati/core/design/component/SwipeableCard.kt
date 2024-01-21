package com.memorati.core.design.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissValue
import androidx.compose.material3.rememberSwipeToDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SwipeableCard(
    order: Int,
    count: Int,
    onSwipeStart: () -> Boolean,
    onSwipeEnd: () -> Boolean,
    initialValue: SwipeToDismissValue = SwipeToDismissValue.Settled,
    content: @Composable RowScope.() -> Unit,
) {
    val width = LocalConfiguration.current.screenWidthDp / 2
    val receiver = LocalDensity.current
    val dismissState = rememberSwipeToDismissState(
        initialValue = initialValue,
        positionalThreshold = { with(receiver) { width.dp.toPx() } },
        confirmValueChange = { swipeToDismissValue ->
            when (swipeToDismissValue) {
                SwipeToDismissValue.EndToStart -> onSwipeStart()
                SwipeToDismissValue.StartToEnd -> onSwipeEnd()
                else -> false
            }
        },
    )

    val animatedScale by animateFloatAsState(
        targetValue = 1f - (count - order) * 0.05f,
        label = "Scale",
    )

    val animatedYOffset by animateDpAsState(
        targetValue = ((count - order) * -16).dp,
        label = "OffsetY",
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier
            .offset { IntOffset(x = 0, y = animatedYOffset.roundToPx()) }
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            },
        content = content,
        backgroundContent = {},
    )
}
