package com.memorati.feature.creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import com.memorati.feature.creation.navigation.CARD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
class CardCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val flashcard = flashcardsRepository
        .findById(checkNotNull(savedStateHandle[CARD_ID]))
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null,
        )

    fun createCard(front: String, back: String) = viewModelScope.launch {
        flashcardsRepository.createCard(
            flashcard.value?.copy(
                front = front,
                back = back,
            ) ?: Flashcard(
                id = 0,
                front = front,
                back = back,
                createdAt = Clock.System.now(),
                lastReviewAt = Clock.System.now(),
                nextReviewAt = Clock.System.now().plus(6.hours),
            ),
        )
    }
}
