package com.memorati.core.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "topics",
)
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "topic_id")
    val topicId: Long,
    val label: String,
)
