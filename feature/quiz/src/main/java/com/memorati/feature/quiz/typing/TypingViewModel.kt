package com.memorati.feature.quiz.typing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.algorithm.answer
import com.memorati.core.common.map.mutate
import com.memorati.core.common.viewmodel.launch
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.Flashcard
import com.memorati.feature.quiz.typing.model.Typing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TypingViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val typings = MutableStateFlow(mapOf<Long, Typing>())
    val state = combine(
        flashcardsRepository.flashcards(),
        typings,
    ) { cards, typings ->
        cards.sortedWith(
            compareBy(
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(3).reversed().map { card ->
            typings[card.id] ?: Typing(card = card)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList(),
        )

    fun onTyping(card: Flashcard, idiom: String) {
        val percentage = if (idiom.isBlank()) {
            1f
        } else {
            minOf(1f, idiom.length.toFloat() / card.idiom.length)
        }
        val state = when {
            idiom.isBlank() -> Typing.State.DEFAULT
            card.idiom.contains(idiom.trim(), true) -> Typing.State.CORRECT
            else -> Typing.State.WRONG
        }

        typings.update {
            it.mutate {
                put(
                    card.id,
                    Typing(
                        card = card,
                        state = state,
                        percentage = percentage,
                        idiom = idiom,
                    ),
                )
            }
        }
    }

    fun onNext(typing: Typing) = launch {
        typings.update {
            it.mutate {
                this[typing.card.id] = typing.copy(swipeState = Typing.SwipeState.SWIPE)
            }
        }

        flashcardsRepository.updateCard(
            typing.card.answer(
                typing.card.idiom.equals(
                    typing.idiom.trim(),
                    ignoreCase = true,
                ),
            ),
        )
    }
}
