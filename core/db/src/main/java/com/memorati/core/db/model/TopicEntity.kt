package com.memorati.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "topics",
)
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    val topicId: Long,
    val label: String,
)
