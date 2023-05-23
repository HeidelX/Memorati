package com.memorati.core.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "flashcards_topics",
    primaryKeys = ["flashcardId", "topicId"]
)
data class FlashcardTopicCrossRef(
    val flashcardId: Long,
    val topicId: Long,
)


data class FlashcardsWithTopics(
    @Embedded val flashcard: FlashcardEntity,
    @Relation(
        parentColumn = "flashcardId",
        entityColumn = "topicId",
        associateBy = Junction(FlashcardTopicCrossRef::class)
    )
    val topics: List<TopicEntity>
)