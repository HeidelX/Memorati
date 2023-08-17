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
