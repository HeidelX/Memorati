package com.memorati.feature.assistant.time

import kotlinx.datetime.LocalTime

fun inRange(time: LocalTime, startTime: LocalTime, endTime: LocalTime): Boolean {
    return when {
        startTime > endTime -> {
            time in startTime..LocalTime(23, 59, 59, 999_999_999) ||
                time in LocalTime(0, 0)..endTime
        }

        else -> time in startTime..endTime
    }
}
