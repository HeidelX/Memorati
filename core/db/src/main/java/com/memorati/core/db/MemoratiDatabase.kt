package com.memorati.core.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.memorati.core.db.converter.InstantConverter
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.Flashcard

@Database(
    entities = [
        Flashcard::class,
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

fun memoratiDb(applicationContext: Application): MemoratiDatabase = Room.databaseBuilder(
    applicationContext,
    MemoratiDatabase::class.java,
    "memorati-database",
).build()
