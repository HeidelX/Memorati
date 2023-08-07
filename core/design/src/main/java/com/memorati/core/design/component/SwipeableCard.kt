package com.memorati.core.design.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
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
    onSwipeStart: () -> Unit,
    onSwipeEnd: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val width = LocalConfiguration.current.screenWidthDp / 2
    val receiver = LocalDensity.current
    val dismissState = rememberDismissState(
        positionalThreshold = { with(receiver) { width.dp.toPx() } },
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.DismissedToStart -> onSwipeStart()
                DismissValue.DismissedToEnd -> onSwipeEnd()
                else -> Unit
            }
            true
        },
    )
    val animatedScale by animateFloatAsState(
        targetValue = 1f - (count - order) * 0.05f,
        label = "Scale",
    )
    val animatedYOffset by animateDpAsState(
        targetValue = ((count - order) * -12).dp,
        label = "OffsetY",
    )

    SwipeToDismiss(
        modifier = Modifier
            .offset { IntOffset(x = 0, y = animatedYOffset.roundToPx()) }
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            },
        state = dismissState,
        dismissContent = content,
        background = {},
    )
}
