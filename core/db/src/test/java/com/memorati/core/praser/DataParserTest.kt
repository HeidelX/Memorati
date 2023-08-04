package com.memorati.core.praser

import com.memorati.core.db.transfer.DataTransferV1
import com.memorati.core.db.transfer.DataTransferV2
import kotlin.test.Test
import kotlin.test.assertTrue

class DataParserTest {
    private val dataParser = DataParser()

    @Test
    fun encode() {
        // dataParser.encode()
    }

    @Test
    fun `Parse data version 1`() {
        val json = javaClass.classLoader?.getResourceAsStream(
            "flashcards_v1.json",
        )!!.bufferedReader().readText()

        val dataTransfer = dataParser.parseData(json)
        assertTrue { dataTransfer is DataTransferV1 }
        assertTrue { dataTransfer.cards.size == 241 }
        assertTrue { dataTransfer.version == "1" }
    }

    @Test
    fun `Parse data version 2`() {
        val json = javaClass.classLoader?.getResourceAsStream(
            "flashcards_v2.json",
        )!!.bufferedReader().readText()

        dataParser.parseData(json)

        val dataTransfer = dataParser.parseData(json)
        assertTrue { dataTransfer is DataTransferV2 }
        assertTrue { dataTransfer.cards.size == 151 }
        assertTrue { dataTransfer.version == "2" }
    }
}
