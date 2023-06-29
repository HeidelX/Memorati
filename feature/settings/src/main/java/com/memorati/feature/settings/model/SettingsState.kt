package com.memorati.feature.settings.model

import com.memorati.core.model.UserData

data class SettingsState(
    val flashcardsCount: Int,
    val userData: UserData = UserData(),
    val error: Exception? = null,
    val notificationsEnabled: Boolean = true,
)
