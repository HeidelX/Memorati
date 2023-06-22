package com.memorati.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.file.MemoratiFileProvider
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.feature.settings.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    private val memoratiFileProvider: MemoratiFileProvider,
) : ViewModel() {
    val settings = flashcardsRepository.flashcards().map { flashcards ->

        SettingsState(
            flashcardsCount = flashcards.size,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState(flashcardsCount = 0),
    )

    fun exportFlashcards(title: String) = viewModelScope.launch {
        val flashcards = flashcardsRepository.flashcards().first()
        memoratiFileProvider.intentProvider(file = File(""), title = title)
    }
}
