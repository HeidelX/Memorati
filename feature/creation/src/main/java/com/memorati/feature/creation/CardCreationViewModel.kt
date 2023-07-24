package com.memorati.feature.creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.common.Defaults.REVIEW_DURATION
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
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
    private val preferencesDataSource: PreferencesDataSource,
    private val cardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val idiom = MutableStateFlow("")
    private val meaning = MutableStateFlow("")
    private val flashcard = cardsRepository.findById(checkNotNull(savedStateHandle[CARD_ID]))
    private val suggestions = idiom.mapLatest { query ->
        if (query.isEmpty()) emptyList() else cardsRepository.searchBy(query).map { it.idiom }
    }

    init {
        viewModelScope.launch {
            flashcard.first()?.let { card ->
                idiom.update { card.idiom }
                meaning.update { card.meaning }
            }
        }
    }

    val state = combine(
        flashcard,
        idiom,
        meaning,
        suggestions,
    ) { flashcard, idiom, description, suggestions ->
        CreationState(
            idiom = idiom,
            meaning = description,
            suggestions = suggestions,
            editMode = flashcard != null,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CreationState(),
    )

    fun save() = launch {
        val idiom = idiom.value.trim()
        val meaning = meaning.value.trim()
        val idiomLanguageTag = preferencesDataSource.userData.first().idiomLanguageTag
        cardsRepository.createCard(
            flashcard.first()?.copy(
                idiom = idiom,
                meaning = meaning,
            ) ?: Flashcard(
                id = 0,
                idiom = idiom,
                meaning = meaning,
                createdAt = Clock.System.now(),
                lastReviewAt = Clock.System.now(),
                nextReviewAt = Clock.System.now().plus(REVIEW_DURATION),
                idiomLanguageTag = idiomLanguageTag,
            ),
        )
    }

    fun onIdiomChange(newIdiom: String) {
        idiom.update { newIdiom }
    }

    fun onDescriptionChange(newDescription: String) {
        meaning.update { newDescription }
    }

    fun setIdiomLanguageTag(tag: String) = launch {
        preferencesDataSource.setIdiomLanguageTag(tag)
    }
}
