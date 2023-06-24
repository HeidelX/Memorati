package com.memorati.feature.settings.model

import kotlinx.datetime.LocalTime

data class SettingsState(
    val flashcardsCount: Int,
    val startTime: LocalTime = LocalTime(hour = 9, minute = 0),
    val endTime: LocalTime = LocalTime(hour = 18, minute = 0),
    val error: Exception? = null,
)
