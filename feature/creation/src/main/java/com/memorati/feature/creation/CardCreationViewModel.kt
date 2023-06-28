package com.memorati.feature.creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.Defaults.REVIEW_DURATION
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import com.memorati.feature.creation.model.CreationState
import com.memorati.feature.creation.navigation.CARD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class CardCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val idiom = MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val flashcard = cardsRepository.findById(checkNotNull(savedStateHandle[CARD_ID]))
    private val suggestions = idiom.mapLatest { query ->
        if (query.isEmpty()) emptyList() else cardsRepository.searchBy(query).map { it.front }
    }

    init {
        viewModelScope.launch {
            flashcard.first()?.let { card ->
                idiom.update { card.front }
                description.update { card.back }
            }
        }
    }

    val state = combine(idiom, description, suggestions) { idiom, description, suggestions ->
        CreationState(
            idiom = idiom,
            description = description,
            suggestions = suggestions,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CreationState(),
    )

    fun save() {
        viewModelScope.launch {
            cardsRepository.createCard(
                flashcard.first()?.copy(
                    front = idiom.value,
                    back = description.value,
                ) ?: Flashcard(
                    id = 0,
                    front = idiom.value,
                    back = description.value,
                    createdAt = Clock.System.now(),
                    lastReviewAt = Clock.System.now(),
                    nextReviewAt = Clock.System.now().plus(REVIEW_DURATION),
                ),
            )
        }
    }

    fun onIdiomChange(newIdiom: String) {
        idiom.update { newIdiom }
    }

    fun onDescriptionChange(newDescription: String) {
        description.update { newDescription }
    }
}
