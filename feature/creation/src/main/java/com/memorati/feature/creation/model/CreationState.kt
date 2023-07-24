package com.memorati.feature.creation.model

data class CreationState(
    val idiom: String = "",
    val meaning: String = "",
    val suggestions: List<String> = emptyList(),
    val editMode: Boolean = false,
) {
    val isValid get() = idiom.isNotBlank() && meaning.isNotBlank()
}
