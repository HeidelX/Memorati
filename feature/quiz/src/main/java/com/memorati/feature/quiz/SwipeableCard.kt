package com.memorati.feature.quiz

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun SwipeableCard(
    order: Int,
    count: Int,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val animatedScale by animateFloatAsState(
        targetValue = 1f - (count - order) * 0.05f,
        label = "Scale",
    )
    val animatedYOffset by animateDpAsState(
        targetValue = ((count - order) * -12).dp,
        label = "OffsetY",
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(x = 0, y = animatedYOffset.roundToPx()) }
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            }
            .swipe(
                onSwipeLeft = onSwipeLeft,
                onSwipeRight = onSwipeRight,
            ),
        content = content,
    )
}

fun Modifier.swipe(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    var clearedHurdle by remember { mutableStateOf(false) }
    pointerInput(Unit) {
        val decay = splineBasedDecay<Float>(this)

        coroutineScope {
            while (true) {
                offsetX.stop()
                val velocityTracker = VelocityTracker()
                awaitPointerEventScope {
                    horizontalDrag(awaitFirstDown().id) { change ->
                        Log.d("AAA", change.toString())
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        launch { offsetX.snapTo(horizontalDragOffset) }
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                    }
                }

                val velocity = velocityTracker.calculateVelocity().x
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
                if (targetOffsetX.absoluteValue <= size.width) {
                    launch { offsetX.animateTo(targetValue = 0f, initialVelocity = velocity) }
                } else {
                    val boomerangDuration = 600
                    val maxDistanceToFling = (size.width * 4).toFloat()
                    val easeInOutEasing = CubicBezierEasing(1.0f, 1.0f, 1.0f, 1.0f)

                    val distanceToFling = min(
                        targetOffsetX.absoluteValue + size.width,
                        maxDistanceToFling,
                    )

                    val animationJob = launch {
                        offsetX.animateTo(
                            targetValue = 0f,
                            initialVelocity = velocity,
                            animationSpec = keyframes {
                                durationMillis = boomerangDuration
                                distanceToFling at (boomerangDuration / 2) with easeInOutEasing
                                40f at boomerangDuration - 70
                            },
                        ) {
                            if (value <= size.width * 2 && !clearedHurdle) {
                                if (offsetX.value <= size.width / 2) onSwipeRight() else onSwipeLeft()
                                clearedHurdle = true
                            }
                        }
                    }

                    animationJob.join()
                    clearedHurdle = false
                }
            }
        }
    }
        .offset {
            IntOffset(offsetX.value.roundToInt(), 0)
        }
        .graphicsLayer {
            transformOrigin = TransformOrigin.Center
        }
}
