package com.memorati.core.db.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class AdditionalInfoEntityTest {

    @Test
    fun `Encode AdditionalInfoEntity to string`() {
        assertEquals(
            """
            {"difficulty":2.0,"consecutiveCorrectCount":0,"memoryStrength":1.0}
            """.trimIndent(),
            Json.encodeToString(
                AdditionalInfoEntity(
                    difficulty = 2.0,
                ),
            ),
        )

        assertEquals(
            """
            {"difficulty":1.0,"consecutiveCorrectCount":0,"memoryStrength":1.0}
            """.trimIndent(),
            Json.encodeToString(
                AdditionalInfoEntity(),
            ),
        )
    }

    @Test
    fun `Decode string to AdditionalInfoEntity `() {
        assertEquals(
            AdditionalInfoEntity(
                difficulty = 2.0,
            ),
            Json.decodeFromString(
                """
            {"difficulty":2.0,"consecutiveCorrectCount":0,"memoryStrength":1.0}
                """.trimIndent(),
            ),
        )

        assertEquals(
            AdditionalInfoEntity(),
            Json.decodeFromString(
                """
            {"difficulty":1.0,"consecutiveCorrectCount":0,"memoryStrength":1.0}
                """.trimIndent(),
            ),
        )
    }
}
