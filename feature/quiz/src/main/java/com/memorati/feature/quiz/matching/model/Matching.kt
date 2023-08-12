package com.memorati.feature.quiz.matching.model

data class Matching(
    private val idiomId: Long = Long.MIN_VALUE,
    private val meaningId: Long = Long.MIN_VALUE,
    private val ids: List<Long> = emptyList(),
) {
    fun idiomId(id: Long) = copy(
        idiomId = id,
        ids = if (id == meaningId) ids + id else ids,
    )

    fun meaningId(id: Long) = copy(
        meaningId = id,
        ids = if (idiomId == id) ids + id else ids,
    )

    fun idiomId() = idiomId
    fun meaningId() = meaningId

    operator fun contains(id: Long) = ids.contains(id)
}
