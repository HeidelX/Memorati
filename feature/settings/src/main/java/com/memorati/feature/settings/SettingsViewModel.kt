package com.memorati.feature.settings

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.file.MemoratiFileProvider
import com.memorati.core.data.repository.DataTransferRepository
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.feature.settings.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    private val dataTransferRepository: DataTransferRepository,
    private val memoratiFileProvider: MemoratiFileProvider,
    private val preferencesDataSource: PreferencesDataSource,
) : ViewModel() {

    private val error = MutableStateFlow<Exception?>(null)
    val settings = combine(
        flashcardsRepository.flashcards(),
        preferencesDataSource.userData,
        error,
    ) { flashcards, userData, error ->
        SettingsState(
            flashcardsCount = flashcards.size,
            userData = userData,
            error = error,
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

    fun onTimeSelected(
        request: TimePickerRequest,
        hour: Int,
        minute: Int,
    ) {
        viewModelScope.launch {
            val time = LocalTime(hour, minute).toMillisecondOfDay()
            when (request) {
                TimePickerRequest.START -> preferencesDataSource.setStartTime(time)
                TimePickerRequest.END -> preferencesDataSource.setEndTime(time)
                TimePickerRequest.DISMISS -> Unit
            }
        }
    }

    fun onAlarmIntervalSelected(hour: Int, minute: Int) {
        viewModelScope.launch {
            preferencesDataSource.setAlarmInterval(
                hour.hours.plus(minute.minutes).inWholeMilliseconds
            )
        }
    }
}
