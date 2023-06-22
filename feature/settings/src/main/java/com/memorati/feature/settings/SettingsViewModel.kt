package com.memorati.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.feature.settings.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
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
}
