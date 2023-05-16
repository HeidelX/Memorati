package com.memorati.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.memorati.core.db.model.FlashcardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardsDao {
    @Query("SELECT * FROM flashcards")
    fun getAll(): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE favoured = 1")
    fun getFavourites(): Flow<List<FlashcardEntity>>

    @Insert
    suspend fun insert(flashcardEntity: FlashcardEntity)

    @Update
    suspend fun update(flashcardEntity: FlashcardEntity)

    @Delete
    suspend fun delete(flashcardEntity: FlashcardEntity)
}
