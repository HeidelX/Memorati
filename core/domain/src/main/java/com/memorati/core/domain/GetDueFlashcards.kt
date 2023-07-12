package com.memorati.core.domain

import com.memorati.core.common.dispatcher.Dispatcher
import com.memorati.core.common.dispatcher.MemoratiDispatchers.IO
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.model.AssistantCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDueFlashcards @Inject constructor(
    private val flashcardsRepository: FlashcardsRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<AssistantCard>> = flow {
        val cards = withContext(ioDispatcher) {
            val backs = flashcardsRepository.flashcards()
                .map { cards -> cards.map { card -> card.back } }
                .first()
            flashcardsRepository.dueFlashcards()
                .first()
                .map { card ->
                    val rest = backs.filterNot { back -> back == card.back }
                    AssistantCard(
                        flashcard = card,
                        answers = rest.assistantAnswers().plus(card.back).shuffled(),
                    )
                }
        }
        emit(cards)
    }
}

internal fun List<String>.assistantAnswers(): List<String> = when {
    isEmpty() -> emptyList()
    size == 1 -> listOf(random())
    else -> asSequence().shuffled().take(2).toList()
}
