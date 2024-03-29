package com.memorati.core.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "flashcards_topics",
    primaryKeys = ["flashcard_id", "topic_id"],
)
data class FlashcardTopicCrossRef(
    @ColumnInfo(name = "flashcard_id")
    val flashcardId: Long,

    @ColumnInfo(name = "topic_id", index = true)
    val topicId: Long,
)

data class FlashcardsWithTopics(
    @Embedded val flashcard: FlashcardEntity,
    @Relation(
        parentColumn = "flashcard_id",
        entityColumn = "topic_id",
        associateBy = Junction(FlashcardTopicCrossRef::class),
    )
    val topics: List<TopicEntity>,
)
