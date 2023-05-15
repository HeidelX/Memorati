package com.memorati.feature.cards

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.memorati.core.data.repository.FlashcardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    flashcardsRepository: FlashcardsRepository,
) : ViewModel() {
    val cards = flashcardsRepository.flashcards()
}
