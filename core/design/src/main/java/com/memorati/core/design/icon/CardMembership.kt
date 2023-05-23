package com.memorati.core.design.icon

import MemoratiIcons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val MemoratiIcons.CardMembership: ImageVector
    get() {
        if (_card_membership != null) {
            return _card_membership!!
        }
        _card_membership = materialIcon(name = "CardMembership") {
            materialPath {
                moveTo(4f, 13f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(16f)
                verticalLineToRelative(-2f)
                close()
                moveToRelative(5.45f, 8.275f)
                quadToRelative(-0.5f, 0.25f, -0.975f, -0.038f)
                quadTo(8f, 20.95f, 8f, 20.375f)
                verticalLineTo(17f)
                horizontalLineTo(4f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                quadTo(2f, 15.825f, 2f, 15f)
                verticalLineTo(4f)
                quadToRelative(0f, -0.825f, 0.588f, -1.413f)
                quadTo(3.175f, 2f, 4f, 2f)
                horizontalLineToRelative(16f)
                quadToRelative(0.825f, 0f, 1.413f, 0.587f)
                quadTo(22f, 3.175f, 22f, 4f)
                verticalLineToRelative(11f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                quadTo(20.825f, 17f, 20f, 17f)
                horizontalLineToRelative(-4f)
                verticalLineToRelative(3.375f)
                quadToRelative(0f, 0.575f, -0.475f, 0.862f)
                quadToRelative(-0.475f, 0.288f, -0.975f, 0.038f)
                lineTo(12f, 20f)
                close()
                moveTo(4f, 10f)
                horizontalLineToRelative(16f)
                verticalLineTo(4f)
                horizontalLineTo(4f)
                close()
                moveToRelative(0f, 5f)
                verticalLineTo(4f)
                verticalLineToRelative(11f)
                close()
            }
        }
        return _card_membership!!
    }

private var _card_membership: ImageVector? = null
