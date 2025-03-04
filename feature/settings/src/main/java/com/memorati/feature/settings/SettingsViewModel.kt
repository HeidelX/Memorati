package com.memorati.feature.settings

import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.file.MemoratiFileProvider
import com.memorati.core.common.flow.tickerFlow
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.DataTransferRepository
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.feature.settings.chart.DayEntry
import com.memorati.feature.settings.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaInstant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.math.max
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    private val dataTransferRepository: DataTransferRepository,
    private val memoratiFileProvider: MemoratiFileProvider,
    private val preferencesDataSource: PreferencesDataSource,
    private val notificationManagerCompat: NotificationManagerCompat,
) : ViewModel() {

    private val notificationsEnabled = tickerFlow().map {
        notificationManagerCompat.areNotificationsEnabled()
    }
    private val error = MutableStateFlow<Exception?>(null)
    val settings = combine(
        flashcardsRepository.flashcards(),
        preferencesDataSource.userData,
        notificationsEnabled,
        error,
    ) { flashcards, userData, notificationsEnabled, error ->

        val creationMap = mutableMapOf<LocalDate, Int>()
        val reviewMap = mutableMapOf<LocalDate, Int>()

        flashcards.forEach { card ->
            val creationDate = card.createdAt.localDate
            creationMap[creationDate] = creationMap.getOrDefault(creationDate, 0) + 1

            val reviewDate = card.lastReviewAt.localDate
            reviewMap[reviewDate] = reviewMap.getOrDefault(reviewDate, 0) + 1
        }

        val chartEntries = creationMap.keys
            .plus(reviewMap.keys)
            .map {
                DayEntry(
                    date = it,
                    creationCount = creationMap.getOrDefault(it, 0),
                    reviewCount = reviewMap.getOrDefault(it, 0),
                )
            }.sortedBy { it.date }

        SettingsState(
            userData = userData,
            flashcardsCount = flashcards.size,
            memorizationLevel = flashcards.map { card ->
                card.additionalInfo.consecutiveCorrectCount.toFloat()
                    .div(max(card.additionalInfo.totalReviews, userData.wordCorrectnessCount))
                    .coerceIn(0f, 1f)
            }.average().toFloat(),
            chartEntries = chartEntries,
            notificationsEnabled = notificationsEnabled,
            error = error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState(flashcardsCount = 0, memorizationLevel = 0.0f),
    )

    fun exportFlashcards(title: String, context: Context) = launch {
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

    fun importFile(uri: Uri?) = launch {
        try {
            uri?.let { dataTransferRepository.import(uri.toString()) }
        } catch (e: Exception) {
            error.value = e
        }
    }

    fun clearData() = launch {
        flashcardsRepository.clear()
    }

    fun onTimeSelected(
        request: TimePickerRequest,
        hour: Int,
        minute: Int,
    ) = launch {
        val time = LocalTime(hour, minute).toMillisecondOfDay()
        when (request) {
            TimePickerRequest.START -> preferencesDataSource.setStartTime(time)
            TimePickerRequest.END -> preferencesDataSource.setEndTime(time)
            TimePickerRequest.DISMISS -> Unit
        }
    }

    fun onDurationSelected(
        hours: Int,
        minutes: Int,
    ) = launch {
        preferencesDataSource.setAlarmInterval(
            hours.hours.plus(minutes.minutes).inWholeMilliseconds,
        )
    }

    fun onCorrectnessCountChange(value: Int) = launch {
        preferencesDataSource.setCorrectnessCount(value)
    }

    fun onWeekCountChange(value: Int) = launch {
        preferencesDataSource.setWeekCountOfReview(value)
    }

    private val Instant.localDate: LocalDate
        get() = toJavaInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
}
