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

val MemoratiIcons.Labs: ImageVector
    @Composable
    get() = remember {
        ImageVector.Builder(
            name = "labs",
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

                moveTo(20f, 36.583f)
                quadToRelative(-3.417f, 0f, -5.833f, -2.416f)
                quadToRelative(-2.417f, -2.417f, -2.417f, -5.834f)
                verticalLineTo(13.25f)
                horizontalLineToRelative(-0.125f)
                quadToRelative(-1.333f, 0f, -2.271f, -0.938f)
                quadToRelative(-0.937f, -0.937f, -0.937f, -2.27f)
                verticalLineTo(6.583f)
                quadToRelative(0f, -1.291f, 0.937f, -2.25f)
                quadToRelative(0.938f, -0.958f, 2.271f, -0.958f)
                horizontalLineToRelative(16.792f)
                quadToRelative(1.291f, 0f, 2.25f, 0.958f)
                quadToRelative(0.958f, 0.959f, 0.958f, 2.25f)
                verticalLineToRelative(3.459f)
                quadToRelative(0f, 1.333f, -0.958f, 2.27f)
                quadToRelative(-0.959f, 0.938f, -2.25f, 0.938f)
                horizontalLineToRelative(-0.125f)
                verticalLineToRelative(15.083f)
                quadToRelative(0f, 3.417f, -2.438f, 5.834f)
                quadToRelative(-2.437f, 2.416f, -5.854f, 2.416f)
                close()
                moveToRelative(-8.958f, -25.958f)
                horizontalLineToRelative(17.916f)
                verticalLineTo(6.042f)
                horizontalLineTo(11.042f)
                verticalLineToRelative(4.583f)
                close()
                moveTo(20f, 33.958f)
                quadToRelative(2f, 0f, 3.5f, -1.208f)
                reflectiveQuadToRelative(2f, -3.125f)
                horizontalLineToRelative(-4.125f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadToRelative(0.917f, -0.396f)
                horizontalLineToRelative(4.25f)
                verticalLineToRelative(-2.375f)
                horizontalLineToRelative(-4.25f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadToRelative(0.917f, -0.396f)
                horizontalLineToRelative(4.25f)
                verticalLineToRelative(-2.375f)
                horizontalLineToRelative(-4.25f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadToRelative(0.917f, -0.396f)
                horizontalLineToRelative(4.25f)
                verticalLineToRelative(-3.75f)
                horizontalLineToRelative(-11.25f)
                verticalLineToRelative(15.083f)
                quadToRelative(0f, 2.334f, 1.646f, 3.979f)
                quadToRelative(1.646f, 1.646f, 3.979f, 1.646f)
                close()
                moveToRelative(-8.958f, -23.333f)
                verticalLineTo(6.042f)
                verticalLineToRelative(4.583f)
                close()
            }
        }.build()
    }
