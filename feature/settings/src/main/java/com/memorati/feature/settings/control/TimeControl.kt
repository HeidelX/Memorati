package com.memorati.feature.settings.control

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
fun TimeControl(
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.surface
    val arc = MaterialTheme.colorScheme.primary
    Canvas(modifier = modifier.sizeIn(150.dp, 150.dp)) {
        val width = size.width

        val radius = width / 2 - 300

        drawCircle(color = background)

        drawArc(
            brush = Brush.linearGradient(listOf(arc, arc.copy(alpha = 0.4f))),
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(radius, radius),
            style = Stroke(width = 50f),
        )
    }
}

@Composable
@DevicePreviews
fun TimeControlPreview() {
    MemoratiTheme {
        TimeControl()
    }
}
