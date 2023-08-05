package com.memorati.core.db.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class AdditionalInfoEntityTest {

    private val infoString1 = """
                {"difficulty":1.0,"consecutiveCorrectCount":0,"memoryStrength":1.0}
    """.trimIndent()
    private val infoString2 = """
                {"difficulty":2.0,"consecutiveCorrectCount":0,"memoryStrength":1.0}
    """.trimIndent()

    @Test
    fun `Encode AdditionalInfoEntity to string`() {
        assertEquals(
            infoString2,
            Json.encodeToString(AdditionalInfoEntity(difficulty = 2.0)),
        )

        assertEquals(
            infoString1,
            Json.encodeToString(AdditionalInfoEntity()),
        )
    }

    @Test
    fun `Decode string to AdditionalInfoEntity `() {
        assertEquals(
            AdditionalInfoEntity(difficulty = 2.0),
            Json.decodeFromString(infoString2),
        )

        assertEquals(
            AdditionalInfoEntity(),
            Json.decodeFromString(infoString1),
        )
    }
}
