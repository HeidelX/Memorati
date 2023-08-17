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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MatchingViewModel @Inject constructor(
    flashcardsRepository: FlashcardsRepository,
) : ViewModel() {

    private val random = Random
    private val matching = MutableStateFlow(Matching())
    private val replay = MutableStateFlow(0)
    private val flashcardsPair = replay.flatMapLatest {
        flashcardsRepository.flashcards().map { cards ->
            cards.shuffled(random).take(3).run { this to shuffled(random) }
        }.take(1)
    }

    val state = combine(
        flashcardsPair,
        matching,
    ) { (idioms, meanings), matching ->
        idioms.map { card ->
            Match(
                id = card.id,
                title = card.idiom,
                type = IDIOM,
                selected = card.id == matching.idiomId(),
                enabled = card.id !in matching,
            )
        } to meanings.map { card ->
            Match(
                id = card.id,
                title = card.meaning,
                type = MEANING,
                selected = card.id == matching.meaningId(),
                enabled = card.id !in matching,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Pair(emptyList(), emptyList()),
    )

    fun onSelect(match: Match) = matching.update { matching ->
        when (match.type) {
            IDIOM -> matching.idiomId(match.id)
            MEANING -> matching.meaningId(match.id)
        }
    }

    fun replay() {
        replay.update { it + 1 }
        matching.update { Matching() }
    }
}
