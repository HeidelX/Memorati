package com.memorati.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.memorati.core.db.model.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicsDao {
    @Query("SELECT * FROM topics")
    fun getAll(): Flow<List<TopicEntity>>

    @Insert
    suspend fun insert(topicEntity: TopicEntity)

    @Update
    suspend fun update(topicEntity: TopicEntity)

    @Delete
    suspend fun delete(topicEntity: TopicEntity)
}
