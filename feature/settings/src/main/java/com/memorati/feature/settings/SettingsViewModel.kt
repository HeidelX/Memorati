package com.memorati.feature.settings

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.file.MemoratiFileProvider
import com.memorati.core.data.repository.DataTransferRepository
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.feature.settings.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    private val dataTransferRepository: DataTransferRepository,
    private val memoratiFileProvider: MemoratiFileProvider,
) : ViewModel() {

    private val error = MutableStateFlow<Exception?>(null)
    val settings = combine(
        flashcardsRepository.flashcards(), error
    ) { flashcards, error ->
        SettingsState(
            flashcardsCount = flashcards.size, error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState(flashcardsCount = 0),
    )

    fun exportFlashcards(title: String, context: Context) = viewModelScope.launch {
        try {
            val file = dataTransferRepository.export()
            val intent = memoratiFileProvider.intentProvider(
                file = file,
                title = title,
            ).intent(context)

            context.startActivity(intent)
        } catch (e: Exception) {
            error.value = e
        }
    }

    fun importFile(uri: Uri?) = viewModelScope.launch {
        try {
            uri?.let { dataTransferRepository.import(uri.toString()) }
        } catch (e: Exception) {
            error.value = e
        }
    }

    fun clearData() = viewModelScope.launch {
        flashcardsRepository.clear()
    }
}
