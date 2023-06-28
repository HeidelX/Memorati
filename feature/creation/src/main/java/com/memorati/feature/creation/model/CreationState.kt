package com.memorati.feature.creation.model

data class CreationState(
    val idiom: String = "",
    val description: String = "",
    val suggestions: List<String> = emptyList(),
) {
    val isValid get() = idiom.isNotBlank() && idiom.isNotBlank()
}
