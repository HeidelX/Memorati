package com.memorati.core.praser

import com.memorati.core.db.transfer.DataTransfer
import com.memorati.core.db.transfer.DataTransferV1
import com.memorati.core.db.transfer.DataTransferV2
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class DataParser @Inject constructor() {

    fun parseData(json: String): DataTransfer {
        val jsonElement = Json.parseToJsonElement(json)
        return when (jsonElement.jsonObject["version"]?.jsonPrimitive?.content) {
            "1" -> parse<DataTransferV1>(json)
            else -> parse<DataTransferV2>(json)
        }
    }

    fun encode(dataTransfer: DataTransfer): String = Json.encodeToString(dataTransfer)

    private inline fun <reified T : DataTransfer> parse(
        json: String,
    ) = Json.decodeFromString<T>(json)
}
