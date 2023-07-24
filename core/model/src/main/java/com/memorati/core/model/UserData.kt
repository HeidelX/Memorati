package com.memorati.core.model

import kotlinx.datetime.LocalTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class UserData(
    val startTime: LocalTime = START,
    val endTime: LocalTime = END,
    val reminderInterval: Duration = INTERVAL,
) {
    companion object {
        val START = LocalTime(18, 0)
        val END = LocalTime(9, 0)
        val INTERVAL = 15.minutes
    }
}
