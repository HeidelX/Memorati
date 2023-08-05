package com.memorati.feature.assistant

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
internal fun FlippableCard(
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

    Card(
        modifier
            .fillMaxSize()
            .graphicsLayer {
                rotationX = progress.value
                cameraDistance = 20 * density
            }
            .heightIn(min = 250.dp),
    ) {
        if (progress.value <= 90.0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        flip = !flip
                    },
            ) {
                front()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationX = 180f }
                    .clickable {
                        flip = !flip
                    },
                contentAlignment = Alignment.Center,
            ) {
                back()
            }
        }
    }
}
