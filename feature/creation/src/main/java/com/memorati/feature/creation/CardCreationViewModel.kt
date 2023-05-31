package com.memorati.feature.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class CardCreationViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    fun createCard(front: String, back: String) = viewModelScope.launch {
        flashcardsRepository.createCard(
            Flashcard(
                id = 0,
                front = front,
                back = back,
                createdAt = Clock.System.now(),
                lastReviewAt = Clock.System.now(),
                nextReviewAt = Clock.System.now(),
            ),
        )
    }
}
