package com.memorati.feature.quiz.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.feature.quiz.matching.model.Match
import com.memorati.feature.quiz.matching.model.Match.Type.IDIOM
import com.memorati.feature.quiz.matching.model.Match.Type.MEANING
import com.memorati.feature.quiz.matching.model.Matching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val matching = MutableStateFlow(Matching())
    private val flashcardsPair = flashcardsRepository.flashcards().map { cards ->
        val flashcards = cards.sortedWith(
            compareBy(
                { card -> card.lastReviewAt },
                { card -> card.additionalInfo.difficulty },
                { card -> card.additionalInfo.memoryStrength },
                { card -> card.additionalInfo.consecutiveCorrectCount },
            ),
        ).shuffled()
            .take(3)

        flashcards to flashcards.shuffled()
    }.take(1)

    val state = combine(
        flashcardsPair,
        matching,
    ) { flashcardsPair, matching ->
        Pair(
            flashcardsPair.first.map { card ->
                Match(
                    id = card.id,
                    title = card.idiom,
                    type = IDIOM,
                    selected = card.id == matching.idiomId(),
                    enabled = card.id !in matching,
                )
            },
            flashcardsPair.second.map { card ->
                Match(
                    id = card.id,
                    title = card.meaning,
                    type = MEANING,
                    selected = card.id == matching.meaningId(),
                    enabled = card.id !in matching,
                )
            },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Pair(emptyList(), emptyList()),
    )

    fun onSelect(match: Match) {
        val newMatching = when (match.type) {
            IDIOM -> matching.value.idiomId(match.id)
            MEANING -> matching.value.meaningId(match.id)
        }

        matching.update { newMatching }
    }
}
