package com.memorati.feature.cards

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val cards = flashcardsRepository.flashcards().map { flashcards ->
        val map = flashcards.groupBy { card ->
            card.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
        }.toSortedMap(compareByDescending { it })

        CardsState(map)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        CardsState(),
    )

    fun toggleFavoured(flashcard: Flashcard) = viewModelScope.launch {
        flashcardsRepository.updateCard(
            flashcard.copy(favoured = !flashcard.favoured),
        )
    }

    fun deleteCard(flashcard: Flashcard) = viewModelScope.launch {
        flashcardsRepository.deleteCard(flashcard)
    }

    fun editCard(flashcard: Flashcard) {
    }
}

data class CardsState(
    val map: Map<LocalDate, List<Flashcard>> = emptyMap(),
)
