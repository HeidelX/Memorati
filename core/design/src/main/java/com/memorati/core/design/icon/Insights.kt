package com.memorati.core.design.icon

import MemoratiIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MemoratiIcons.Insights: ImageVector
    @Composable
    get() = remember {
        ImageVector.Builder(
            name = "insights",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(4.875f, 33.458f)
                quadToRelative(-1.208f, 0f, -2.083f, -0.875f)
                quadToRelative(-0.875f, -0.875f, -0.875f, -2.125f)
                quadToRelative(0f, -1.208f, 0.875f, -2.083f)
                quadToRelative(0.875f, -0.875f, 2.083f, -0.875f)
                quadToRelative(0.208f, 0f, 0.417f, 0.042f)
                quadToRelative(0.208f, 0.041f, 0.5f, 0.125f)
                lineToRelative(8.083f, -8.084f)
                quadToRelative(-0.125f, -0.291f, -0.125f, -0.5f)
                verticalLineToRelative(-0.416f)
                quadToRelative(0f, -1.25f, 0.875f, -2.125f)
                reflectiveQuadToRelative(2.083f, -0.875f)
                quadToRelative(1.209f, 0f, 2.084f, 0.895f)
                quadToRelative(0.875f, 0.896f, 0.875f, 2.105f)
                quadToRelative(0f, 0.166f, -0.125f, 0.916f)
                lineToRelative(4.5f, 4.5f)
                quadToRelative(0.291f, -0.083f, 0.5f, -0.125f)
                quadToRelative(0.208f, -0.041f, 0.416f, -0.041f)
                quadToRelative(0.25f, 0f, 0.438f, 0.041f)
                quadToRelative(0.187f, 0.042f, 0.479f, 0.125f)
                lineToRelative(6.417f, -6.416f)
                quadToRelative(-0.084f, -0.334f, -0.104f, -0.521f)
                quadToRelative(-0.021f, -0.188f, -0.021f, -0.396f)
                quadToRelative(0f, -1.25f, 0.875f, -2.125f)
                reflectiveQuadToRelative(2.083f, -0.875f)
                quadToRelative(1.208f, 0f, 2.104f, 0.875f)
                quadToRelative(0.896f, 0.875f, 0.896f, 2.125f)
                quadToRelative(0f, 1.208f, -0.896f, 2.083f)
                quadToRelative(-0.896f, 0.875f, -2.104f, 0.875f)
                quadToRelative(-0.208f, 0f, -0.417f, -0.02f)
                quadToRelative(-0.208f, -0.021f, -0.5f, -0.146f)
                lineTo(27.792f, 26f)
                quadToRelative(0.083f, 0.292f, 0.104f, 0.479f)
                quadToRelative(0.021f, 0.188f, 0.021f, 0.396f)
                quadToRelative(0f, 1.25f, -0.875f, 2.125f)
                reflectiveQuadToRelative(-2.084f, 0.875f)
                quadToRelative(-1.208f, 0f, -2.083f, -0.875f)
                quadTo(22f, 28.125f, 22f, 26.875f)
                verticalLineToRelative(-0.396f)
                quadToRelative(0f, -0.187f, 0.125f, -0.479f)
                lineToRelative(-4.5f, -4.5f)
                quadToRelative(-0.292f, 0.083f, -0.5f, 0.104f)
                quadToRelative(-0.208f, 0.021f, -0.417f, 0.021f)
                quadToRelative(-0.083f, 0f, -0.916f, -0.125f)
                lineTo(7.75f, 29.583f)
                quadToRelative(0.083f, 0.292f, 0.083f, 0.479f)
                verticalLineToRelative(0.396f)
                quadToRelative(0f, 1.25f, -0.875f, 2.125f)
                reflectiveQuadToRelative(-2.083f, 0.875f)
                close()
                moveToRelative(1.792f, -17.791f)
                lineToRelative(-0.875f, -1.917f)
                lineToRelative(-1.875f, -0.875f)
                lineTo(5.792f, 12f)
                lineToRelative(0.875f, -1.875f)
                lineTo(7.542f, 12f)
                lineToRelative(1.875f, 0.875f)
                lineToRelative(-1.875f, 0.875f)
                close()
                moveTo(25f, 13.625f)
                lineToRelative(-1.333f, -2.875f)
                lineToRelative(-2.834f, -1.292f)
                lineToRelative(2.834f, -1.333f)
                lineTo(25f, 5.25f)
                lineToRelative(1.333f, 2.875f)
                lineToRelative(2.834f, 1.333f)
                lineToRelative(-2.834f, 1.292f)
                close()
            }
        }.build()
    }
