package com.memorati.core.file

import com.memorati.core.common.Defaults.REVIEW_DURATION
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.AdditionalInfoEntity
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.transfer.DataTransferV1
import com.memorati.core.db.transfer.DataTransferV2
import com.memorati.core.db.transfer.TransferableCard
import com.memorati.core.db.transfer.TransferableCardV2
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
            TransferableCardV2(
                idiom = flashcardEntity.idiom,
                meaning = flashcardEntity.meaning,
                idiomLanguageTag = flashcardEntity.idiomLanguageTag,
            )
        }

        return Json.encodeToString(
            DataTransferV2(cards = transferableCards),
        )
    }

    suspend fun import(json: String) {
        val transferableCards = when {
            json.contains("\"version\":\"1\"") ->
                Json.decodeFromString<DataTransferV1>(json).cards.map { v1Card ->
                    TransferableCard(
                        idiom = v1Card.idiom,
                        meaning = v1Card.description,
                        idiomLanguageTag = null,
                    )
                }

            else ->
                Json.decodeFromString<DataTransferV2>(json).cards.map { v2Card ->
                    TransferableCard(
                        idiom = v2Card.idiom,
                        meaning = v2Card.meaning,
                        idiomLanguageTag = v2Card.idiomLanguageTag,
                    )
                }
        }

        transferableCards
            .chunked(30)
            .forEachIndexed { index, chunk ->
                chunk.forEach { card -> insertIfNotExist(card, index) }
            }
    }

    private suspend fun insertIfNotExist(
        card: TransferableCard,
        index: Int,
    ) {
        if (dao.findBy(card.idiom.trim()).isEmpty()) {
            dao.insert(
                FlashcardEntity(
                    flashcardId = 0,
                    createdAt = Clock.System.now(),
                    lastReviewAt = Clock.System.now(),
                    nextReviewAt = Clock.System.now().plus(REVIEW_DURATION * (index + 1)),
                    idiom = card.idiom.trim(),
                    meaning = card.meaning.trim(),
                    favoured = false,
                    additionalInfoEntity = AdditionalInfoEntity(),
                    idiomLanguageTag = null,
                ),
            )
        }
    }
}
