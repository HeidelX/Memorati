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

val MemoratiIcons.CompareArrows: ImageVector
    @Composable get() = remember {
        ImageVector.Builder(
            name = "compare_arrows",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f,
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
                moveTo(13.333f, 33.125f)
                lineToRelative(-1.875f, -1.833f)
                lineToRelative(4.834f, -4.834f)
                horizontalLineTo(3.542f)
                verticalLineToRelative(-2.625f)
                horizontalLineToRelative(12.75f)
                lineTo(11.5f, 19f)
                lineToRelative(1.833f, -1.833f)
                lineToRelative(7.959f, 7.958f)
                close()
                moveToRelative(13.375f, -10.292f)
                lineToRelative(-8f, -8f)
                lineToRelative(8f, -7.958f)
                lineToRelative(1.834f, 1.833f)
                lineToRelative(-4.792f, 4.834f)
                horizontalLineToRelative(12.708f)
                verticalLineToRelative(2.625f)
                horizontalLineTo(23.75f)
                lineTo(28.542f, 21f)
                close()
            }
        }.build()
    }
