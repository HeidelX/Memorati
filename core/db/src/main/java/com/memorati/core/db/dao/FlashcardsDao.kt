package com.memorati.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.memorati.core.db.model.Flashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardsDao {
    @Query("SELECT * FROM flashcards")
    fun getAll(): Flow<List<Flashcard>>

    @Insert
    fun insert(flashcard: Flashcard)

    @Delete
    fun delete(flashcard: Flashcard)
}
