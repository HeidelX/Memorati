package com.memorati.feature.creation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.Defaults.REVIEW_DURATION
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import com.memorati.feature.creation.navigation.CARD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class CardCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val queryFlow = MutableStateFlow<String?>(null)
    val queryResult = queryFlow.mapLatest { query ->
        if (query.isNullOrEmpty()) {
            emptyList()
        } else {
            flashcardsRepository.searchBy(query).map { it.front }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    val flashcard = flashcardsRepository
        .findById(checkNotNull(savedStateHandle[CARD_ID]))
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null,
        )

    fun query(query: String) {
        queryFlow.update { query }
    }

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
                nextReviewAt = Clock.System.now().plus(REVIEW_DURATION),
            ),
        )
    }
}
