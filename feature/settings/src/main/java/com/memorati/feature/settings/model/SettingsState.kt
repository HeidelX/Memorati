package com.memorati.feature.settings.model

import com.memorati.core.model.UserData
import com.memorati.feature.settings.chart.DayEntry

data class SettingsState(
    val flashcardsCount: Int,
    val memorizationLevel: Float,
    val userData: UserData = UserData(),
    val error: Exception? = null,
    val notificationsEnabled: Boolean = true,
    val chartEntries: List<DayEntry> = emptyList(),
)
