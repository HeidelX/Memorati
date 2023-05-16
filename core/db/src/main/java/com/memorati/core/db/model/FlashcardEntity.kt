package com.memorati.core.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "flashcards",
)
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val front: String,
    val back: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant,
    val favoured: Boolean,
)
