package com.memorati.core.design.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun FlippableCard(
    modifier: Modifier = Modifier,
    initialFlipped: Boolean = false,
    front: @Composable BoxScope.() -> Unit,
    back: @Composable BoxScope.() -> Unit,
    onFlip: (Boolean) -> Unit = {},
) {
    var flip by remember { mutableStateOf(initialFlipped) }
    val rotation = animateFloatAsState(
        targetValue = if (flip) 180.0f else 0.0f,
        label = "FloatAsState",
        animationSpec = tween(durationMillis = 5_00),
        finishedListener = { onFlip(flip) },
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationX = rotation.value
                cameraDistance = 20 * density
            }
            .heightIn(min = 250.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(
            width = 1.dp,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        ),
    ) {
        when {
            rotation.value <= 90.0f -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { flip = !flip },
                content = front,
            )

            else -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationX = 180f }
                    .clickable { flip = !flip },
                content = back,
            )
        }
    }
}
