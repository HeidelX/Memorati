package com.memorati.feature.assistant.time

import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TimeTest {

    @Test
    fun `Time in time range start grater than end`() {
        assertFalse {
            inRange(
                time = LocalTime(17, 45),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertFalse {
            inRange(
                time = LocalTime(11, 59),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(23, 59),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(1, 0),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(8, 45),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(5, 45),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(0, 0),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(18, 0),
                endTime = LocalTime(9, 0),
            )
        }
    }

    @Test
    fun `Time in time range start less than end`() {
        assertFalse {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertFalse {
            inRange(
                time = LocalTime(23, 59),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(1, 0),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(8, 45),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(5, 45),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertFalse {
            inRange(
                time = LocalTime(0, 0),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertFalse {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }
        assertFalse {
            inRange(
                time = LocalTime(21, 45),
                startTime = LocalTime(1, 0),
                endTime = LocalTime(9, 0),
            )
        }
    }

    @Test
    fun `Time in time range one minute difference`() {
        assertTrue {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(23, 59),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(1, 0),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(8, 45),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(5, 45),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(0, 0),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(22, 45),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }
        assertTrue {
            inRange(
                time = LocalTime(21, 45),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }

        assertTrue {
            inRange(
                time = LocalTime(9, 0),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }

        assertFalse {
            inRange(
                time = LocalTime(9, 0, 59),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }

        assertFalse {
            inRange(
                time = LocalTime(9, 0, 1),
                startTime = LocalTime(9, 1),
                endTime = LocalTime(9, 0),
            )
        }
    }
}
