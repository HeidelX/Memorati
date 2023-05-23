package com.memorati.core.data.repository

import com.memorati.core.data.mapper.toTopic
import com.memorati.core.data.mapper.toTopicEntity
import com.memorati.core.db.dao.TopicsDao
import com.memorati.core.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalTopicsRepository @Inject constructor(
    private val topicsDao: TopicsDao,
) : TopicsRepository {
    override val topics: Flow<List<Topic>> = topicsDao.getAll().map { topicEntities ->
        topicEntities.map { topicEntity -> topicEntity.toTopic() }
    }

    override suspend fun addTopic(topic: Topic) {
        topicsDao.insert(topic.toTopicEntity())
    }

    override suspend fun deleteTopic(topic: Topic) {
        topicsDao.delete(topic.toTopicEntity())
    }
}
