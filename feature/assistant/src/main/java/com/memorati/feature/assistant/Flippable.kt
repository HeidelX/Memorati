package com.memorati.feature.assistant

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
internal fun Flippable(
    modifier: Modifier = Modifier,
    front: @Composable BoxScope.() -> Unit,
    back: @Composable BoxScope.() -> Unit,
) {
    var flip by remember { mutableStateOf(false) }
    val progress = animateFloatAsState(
        targetValue = if (flip) 180.0f else 0.0f,
        label = "FloatAsState",
        animationSpec = tween(durationMillis = 5_00),
    )
    Box(
        modifier = modifier,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .graphicsLayer {
                    rotationX = progress.value
                    cameraDistance = 20 * density
                }
                .heightIn(min = 250.dp)
                .clip(MaterialTheme.shapes.large)
                .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                .clickable {
                    flip = !flip
                },
        ) {
            if (progress.value <= 90.0f) {
                front()
            } else {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .graphicsLayer { rotationX = 180f },
                    contentAlignment = Alignment.Center,
                ) {
                    back()
                }
            }
        }
    }
}
