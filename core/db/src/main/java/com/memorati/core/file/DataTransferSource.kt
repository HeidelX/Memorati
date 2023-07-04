package com.memorati.core.file

import com.memorati.core.common.Defaults.REVIEW_DURATION
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.AdditionalInfoEntity
import com.memorati.core.db.model.DataTransfer
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.model.TransferableCard
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataTransferSource @Inject constructor(
    private val dao: FlashcardsDao,
) {
    suspend fun flashcardsJson(): String {
        val transferableCards = dao.allFlashcard().first().map { flashcardEntity ->
            TransferableCard(
                idiom = flashcardEntity.front,
                description = flashcardEntity.back,
            )
        }

        return Json.encodeToString(
            DataTransfer(cards = transferableCards),
        )
    }

    suspend fun import(json: String) {
        val dataTransfer = Json.decodeFromString<DataTransfer>(json)
        dataTransfer.cards
            .chunked(30)
            .forEachIndexed { index, chunk ->
                chunk.forEach { card ->
                    if (dao.findBy(card.idiom.trim()).isEmpty()) {
                        dao.insert(
                            FlashcardEntity(
                                flashcardId = 0,
                                createdAt = Clock.System.now(),
                                lastReviewAt = Clock.System.now(),
                                nextReviewAt = Clock.System.now()
                                    .plus(REVIEW_DURATION * (index + 1)),
                                front = card.idiom.trim(),
                                back = card.description.trim(),
                                favoured = false,
                                additionalInfoEntity = AdditionalInfoEntity(),
                            ),
                        )
                    } // else scape repeated card
                }
            }
    }
}
