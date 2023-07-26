package com.memorati.core.common.time

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface AppTime {
    val now: Instant
}

internal object DefaultAppTime : AppTime {
    override val now: Instant
        get() = Clock.System.now()
}
