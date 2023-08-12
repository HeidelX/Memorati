package com.memorati.feature.quiz.matching.model

data class Match(
    val id: Long,
    val title: String,
    val type: Type,
    val selected: Boolean = false,
    val enabled: Boolean = true,
) {
    enum class Type {
        IDIOM,
        MEANING,
    }
}

data class Matching(
    private val idiomId: Long = Long.MIN_VALUE,
    private val meaningId: Long = Long.MIN_VALUE,
    private val ids: List<Long> = emptyList(),
) {
    fun idiomId(id: Long): Matching {
        return copy(
            idiomId = id,
            ids = if (id == meaningId) ids + id else ids,
        )
    }

    fun idiomId() = idiomId
    fun meaningId() = meaningId

    fun meaningId(id: Long): Matching {
        return copy(
            meaningId = id,
            ids = if (idiomId == id) ids + id else ids,
        )
    }

    operator fun contains(id: Long) = ids.contains(id)
}
