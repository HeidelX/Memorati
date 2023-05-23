package com.memorati.core.data.mapper

import com.memorati.core.db.model.TopicEntity
import com.memorati.core.model.Topic

fun TopicEntity.toTopic() = Topic(
    id = topicId,
    label = label,
)

fun Topic.toTopicEntity() = TopicEntity(
    topicId = id,
    label = label,
)
