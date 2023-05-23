package com.memorati.core.data.repository

import com.memorati.core.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicsRepository {
    val topics: Flow<List<Topic>>
    suspend fun addTopic(topic: Topic)
    suspend fun deleteTopic(topic: Topic)
}
