package com.memorati.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.memorati.core.db.converter.InstantConverter
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.FlashcardEntity

@Database(
    entities = [
        FlashcardEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        InstantConverter::class,
    ],
)
abstract class MemoratiDatabase : RoomDatabase() {
    abstract fun flashCardsDao(): FlashcardsDao
}
