package com.memorati.feature.cards

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
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

    private val queryFlow = MutableStateFlow("")
    val state = combine(
        flashcardsRepository.flashcards(),
        queryFlow,
    ) { flashcards, query ->
        val map = flashcards.filter { flashcard ->
            if (query.isEmpty()) true else flashcard.contains(query)
        }.groupBy { card ->
            card.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
        }.toSortedMap(compareByDescending { it })

        CardsState(
            map = map,
            query = query,
        )
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

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }
}

private fun Flashcard.contains(query: String): Boolean {
    return back.contains(query, true) || front.contains(query, true)
}

data class CardsState(
    val map: Map<LocalDate, List<Flashcard>> = emptyMap(),
    val query: String = "",
)
