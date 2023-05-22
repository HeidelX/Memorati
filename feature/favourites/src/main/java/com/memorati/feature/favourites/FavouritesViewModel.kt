package com.memorati.feature.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val cards = flashcardsRepository.favourites()

    fun toggleFavoured(flashcard: Flashcard) = viewModelScope.launch {
        flashcardsRepository.updateCard(
            flashcard.copy(favoured = !flashcard.favoured),
        )
    }
}
