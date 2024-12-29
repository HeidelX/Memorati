package com.memorati.feature.settings.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.settings.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
internal fun Chart(
    modifier: Modifier = Modifier,
    entries: List<DayEntry>,
) {
    val lineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    val borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    val lazyListState = rememberLazyListState()

    Column {
        LazyRow(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .drawBehind { if (entries.isNotEmpty()) drawGraph(lineColor, borderColor) }
                .wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
            state = lazyListState,
        ) {
            items(entries) { entry ->
                DayBar(
                    entry = entry,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        ChartInfo(
            color = MaterialTheme.colorScheme.secondaryContainer,
            text = stringResource(R.string.additions_day),

            )
        ChartInfo(
            color = MaterialTheme.colorScheme.inversePrimary,
            text = stringResource(R.string.reviews_day),
        )
    }

    LaunchedEffect(entries) {
        lazyListState.animateScrollToItem(entries.size)
    }
}

@Composable
private fun ChartInfo(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    text: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .padding(5.dp)
                .background(color)
                .size(10.dp),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
private fun DayBar(
    modifier: Modifier = Modifier,
    entry: DayEntry,
    height: Dp = 200.dp,
) {
    val creationHeight = remember(entry) { entry.creationCount * height.value / 100 }
    val reviewHeight = remember(entry) { entry.reviewCount * height.value / 100 }

    Column(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .height(height),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Bottom,
        ) {
            ChartBar(
                itemHeight = creationHeight,
                value = entry.creationCount,
                color = MaterialTheme.colorScheme.secondaryContainer,
            )

            ChartBar(
                itemHeight = reviewHeight,
                value = entry.reviewCount,
                color = MaterialTheme.colorScheme.inversePrimary,
            )
        }
        Text(
            text = entry.day,
            fontSize = 8.sp,
        )
    }
}

@Composable
private fun ChartBar(
    modifier: Modifier = Modifier,
    itemHeight: Float,
    value: Int,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier
            .height(itemHeight.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        Spacer(
            modifier = Modifier
                .height(itemHeight.dp)
                .width(20.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp),
                ),
        )

        Text(
            modifier = Modifier
                .rotate(270f)
                .align(Alignment.BottomCenter),
            text = value.toString(),
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
    val date: LocalDate,
    val creationCount: Int,
    val reviewCount: Int,
) {
    val day: String = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
}

@DevicePreviews
@LocalePreviews
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
        date = LocalDate.of(2023, 12, 12),
        creationCount = 100,
        reviewCount = 10,
    ),
    DayEntry(
        date = LocalDate.of(2023, 10, 12),
        creationCount = 70,
        reviewCount = 20,
    ),
    DayEntry(
        date = LocalDate.of(2023, 11, 12),
        creationCount = 10,
        reviewCount = 30,
    ),
    DayEntry(
        date = LocalDate.of(2023, 9, 12),
        creationCount = 60,
        reviewCount = 40,
    ),
    DayEntry(
        date = LocalDate.of(2023, 8, 12),
        creationCount = 0,
        reviewCount = 50,
    ),
    DayEntry(
        date = LocalDate.of(2023, 3, 12),
        creationCount = 20,
        reviewCount = 60,
    ),
    DayEntry(
        date = LocalDate.of(2023, 2, 12),
        creationCount = 70,
        reviewCount = 70,
    )
)
