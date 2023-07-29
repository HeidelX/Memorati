package com.memorati.feature.creation.model

data class CreationState(
    val idiom: String = "",
    val meaning: String = "",
    val suggestions: List<String> = emptyList(),
    val editMode: Boolean = false,
    val languages: List<Language> = emptyList(),
    val selectedLanguage: String? = null,
) {
    val isValid get() = idiom.isNotBlank() && meaning.isNotBlank()
}

data class Language(
    val displayName: String,
    val tag: String,
)
