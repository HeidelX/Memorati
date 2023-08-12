package com.memorati.feature.quiz.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.feature.quiz.matching.model.Match
import com.memorati.feature.quiz.matching.model.Match.Type.IDIOM
import com.memorati.feature.quiz.matching.model.Match.Type.MEANING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val idiomMatch = MutableStateFlow(Long.MIN_VALUE)
    private val meaningMatch = MutableStateFlow(Long.MIN_VALUE)
    private val matches = flashcardsRepository.flashcards().map { cards ->
        val flashcards = cards.sortedWith(
            compareBy(
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.difficulty },
                { card -> card.additionalInfo.memoryStrength },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).take(3)

        flashcards.zip(flashcards.shuffled())
    }

    val state = combine(
        matches,
        idiomMatch,
        meaningMatch,
    ) { matches, currentIdiom, currentMeaning ->
        matches.map { (idiomCard, meaningCard) ->
            Match(
                id = idiomCard.id,
                title = idiomCard.idiom,
                type = IDIOM,
                selected = idiomCard.id == currentIdiom,
            ) to Match(
                id = meaningCard.id,
                title = idiomCard.meaning,
                type = MEANING,
                selected = meaningCard.id == currentMeaning,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList(),
    )

    fun onSelect(match: Match) {
        when (match.type) {
            IDIOM -> idiomMatch.update { match.id }
            MEANING -> meaningMatch.update { match.id }
        }
    }
}
