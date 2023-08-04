package com.memorati.core.db

import androidx.room.AutoMigration
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
import com.memorati.core.db.v2.V1V2AutoMigrationSpecs

@Database(
    entities = [
        FlashcardEntity::class,
        TopicEntity::class,
        FlashcardTopicCrossRef::class,
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = V1V2AutoMigrationSpecs::class),
    ],
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
