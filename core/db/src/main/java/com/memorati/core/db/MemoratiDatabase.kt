package com.memorati.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.memorati.core.db.converter.AdditionalInfoConverter
import com.memorati.core.db.converter.InstantConverter
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.dao.TopicsDao
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.model.FlashcardTopicCrossRef
import com.memorati.core.db.model.TopicEntity

@Database(
    entities = [
        FlashcardEntity::class,
        TopicEntity::class,
        FlashcardTopicCrossRef::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        InstantConverter::class,
        AdditionalInfoConverter::class,
    ],
)
abstract class MemoratiDatabase : RoomDatabase() {
    abstract fun flashCardsDao(): FlashcardsDao

    abstract fun topicsDao(): TopicsDao
}
