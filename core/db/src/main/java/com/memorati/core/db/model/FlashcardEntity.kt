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
    @ColumnInfo(name = "flashcard_id")
    val flashcardId: Long,
    @ColumnInfo(name = "created_at") val createdAt: Instant,
    @ColumnInfo(name = "last_review_at") val lastReviewAt: Instant,
    @ColumnInfo(name = "next_review_at") val nextReviewAt: Instant,
    @ColumnInfo(name = "additional_info") val additionalInfoEntity: AdditionalInfoEntity,
    @ColumnInfo(name = "idiom_language_tag") val idiomLanguageTag: String?,
    val idiom: String,
    val meaning: String,
    val favoured: Boolean,
)
