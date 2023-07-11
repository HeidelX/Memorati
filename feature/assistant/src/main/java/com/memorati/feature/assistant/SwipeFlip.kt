package com.memorati.feature.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SwipeFlip(
    modifier: Modifier = Modifier,
    front: @Composable BoxScope.() -> Unit,
    back: @Composable BoxScope.() -> Unit,
) {
    val swipeState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, 180f to 1)
    Box(
        modifier = modifier.swipeable(
            state = swipeState,
            anchors = anchors,
            thresholds = { _, _ -> FractionalThreshold(0.3f) },
            orientation = Orientation.Vertical,
        ),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .graphicsLayer {
                    rotationX = swipeState.offset.value
                    cameraDistance = 10 * density
                }
                .heightIn(min = 250.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
                    shape = RoundedCornerShape(10.dp),
                ),
        ) {
            if (swipeState.offset.value <= 90f) {
                front()
            } else {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer { rotationX = 180f },
                ) {
                    back()
                }
            }
        }
    }
}
