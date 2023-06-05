package com.memorati.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.model.FlashcardsWithTopics
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardsDao {
    @Query("SELECT * FROM flashcards")
    fun allFlashcard(): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE flashcard_id=:id")
    fun find(id: Long): Flow<FlashcardEntity?>

    @Transaction
    @Query("SELECT * FROM flashcards")
    fun allFlashcardWithTopics(): Flow<List<FlashcardsWithTopics>>

    @Query("SELECT * FROM flashcards WHERE favoured = 1")
    fun getFavourites(): Flow<List<FlashcardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flashcardEntity: FlashcardEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(flashcardEntity: FlashcardEntity)

    @Delete
    suspend fun delete(flashcardEntity: FlashcardEntity)
}
