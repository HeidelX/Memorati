package com.memorati

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.navigation.TopDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    val state = flashcardsRepository.flashcardsToReview().map { flashcards ->
        TopDestination.values().map { topDestination ->
            if (topDestination == TopDestination.ASSISTANT) {
                NavBarItem(
                    topDestination = topDestination,
                    showBadge = flashcards.isNotEmpty(),
                )
            } else {
                NavBarItem(
                    topDestination = topDestination,
                    showBadge = false,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TopDestination.toNavItems(),
    )
}
