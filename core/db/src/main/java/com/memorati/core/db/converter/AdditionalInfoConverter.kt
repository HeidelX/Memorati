package com.memorati.core.db.converter

import androidx.room.TypeConverter
import com.memorati.core.db.model.AdditionalInfoEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AdditionalInfoConverter {
    @TypeConverter
    fun stringToInfo(infoString: String?): AdditionalInfoEntity? = infoString?.let {
        Json.decodeFromString(it)
    }

    @TypeConverter
    fun infoToString(info: AdditionalInfoEntity?): String? = info?.let {
        Json.encodeToString(info)
    }
}
