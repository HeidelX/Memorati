package com.memorati.feature.cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.core.model.Flashcard
import com.memorati.core.model.UserData
import com.memorati.feature.cards.speech.Orator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val orator: Orator,
    private val flashcardsRepository: FlashcardsRepository,
    private val userPreferences: PreferencesDataSource,
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")
    val state = combine(
        flashcardsRepository.flashcards(),
        queryFlow,
        userPreferences.userData,
    ) { flashcards, query, userData ->
        val map = flashcards.filter { flashcard ->
            if (query.isEmpty()) true else flashcard.contains(query)
        }.groupBy { card ->
            card.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
        }.toSortedMap(
            compareByDescending { it },
        ).entries.associate { entry ->
            entry.key to entry.value.sortedBy { card -> card.idiom }
        }

        CardsState(map = map, query = query, userData = userData)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        CardsState(),
    )

    fun toggleFavoured(flashcard: Flashcard) = launch {
        flashcardsRepository.updateCard(
            flashcard.copy(favoured = !flashcard.favoured),
        )
    }

    fun deleteCard(flashcard: Flashcard) = launch {
        flashcardsRepository.deleteCard(flashcard)
    }

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun speak(card: Flashcard) {
        try {
            orator.setLanguage(card.idiomLanguageTag!!)
            orator.pronounce(card.idiom)
        } catch (e: Exception) {
            Log.d(
                "CardsViewModel",
                e.localizedMessage ?: e.message ?: e::class.qualifiedName.toString(),
            )
        }
    }
}

private fun Flashcard.contains(query: String): Boolean {
    return meaning.contains(query, true) || idiom.contains(query, true)
}

data class CardsState(
    val map: Map<LocalDate, List<Flashcard>> = emptyMap(),
    val query: String = "",
    val userData: UserData = UserData(),
)
