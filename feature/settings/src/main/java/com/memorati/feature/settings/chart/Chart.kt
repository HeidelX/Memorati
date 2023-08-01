package com.memorati.feature.settings.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun Chart(
    modifier: Modifier = Modifier,
    entries: List<DayEntry>,
) {
    val lineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .drawBehind { if (entries.isNotEmpty()) drawGraph(lineColor, borderColor) }
            .wrapContentHeight()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.Bottom,
    ) {
        entries.forEach { entry ->
            Bar(
                value = entry.count,
                label = entry.day,
            )
        }
    }
}

@Composable
private fun Bar(
    value: Int,
    label: String,
    height: Dp = 200.dp,
) {
    val itemHeight = remember(value) { value * height.value / 100 }
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .height(height),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(itemHeight.dp),
            contentAlignment = Alignment.BottomStart,
        ) {
            Spacer(
                modifier = Modifier
                    .height(itemHeight.dp)
                    .width(30.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f),
                                MaterialTheme.colorScheme.inversePrimary,
                            ),
                        ),
                        shape = MaterialTheme.shapes.small,
                    ),
            )

            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = value.toString(),
                fontSize = 8.sp,
            )
        }
        Text(
            text = label,
            fontSize = 8.sp,
        )
    }
}

private fun DrawScope.drawGraph(
    lineColor: Color,
    borderColor: Color,
) {
    val step = 55
    val borderStroke = 5f
    val canvasWidth = size.width
    val canvasHeight = size.height
    for (x in 0..canvasWidth.toInt() step step) {
        drawLine(
            start = Offset(x = x.toFloat(), y = 0f),
            end = Offset(x = x.toFloat(), y = canvasHeight),
            color = if (x.toFloat() == 0f) borderColor else lineColor,
            strokeWidth = if (x.toFloat() == 0f) borderStroke else 0f,
        )
    }

    for (y in canvasHeight.toInt() downTo 0 step step) {
        drawLine(
            start = Offset(x = 0f, y = y.toFloat()),
            end = Offset(x = canvasWidth, y = y.toFloat()),
            color = if (y.toFloat() == canvasHeight) borderColor else lineColor,
            strokeWidth = if (y.toFloat() == canvasHeight) borderStroke else 0f,
        )
    }
}

data class DayEntry(
    val day: String,
    val count: Int,
)

@DevicePreviews
@Composable
internal fun ChartPreview() {
    MemoratiTheme {
        Surface {
            Chart(
                entries = dayEntries(),
            )
        }
    }
}

@Composable
internal fun dayEntries() = listOf(
    DayEntry(
        day = "12.12.2023",
        count = 100,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 70,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 10,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 60,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 0,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 20,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 70,
    ),
    DayEntry(
        day = "12.12.2023",
        count = 40,
    ),
)
