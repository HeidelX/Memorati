package com.memorati.core.file

import com.memorati.core.common.Defaults.REVIEW_DURATION
import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.AdditionalInfoEntity
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.transfer.DataTransferV2
import com.memorati.core.db.transfer.TransferableCard
import com.memorati.core.db.transfer.TransferableCardV2
import com.memorati.core.praser.DataParser
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import javax.inject.Inject

class DataTransferSource @Inject constructor(
    private val dao: FlashcardsDao,
    private val dataParser: DataParser,
) {
    suspend fun flashcardsJson(): String {
        val cards = dao.allFlashcard().first().map { flashcardEntity ->
            TransferableCardV2(
                idiom = flashcardEntity.idiom,
                meaning = flashcardEntity.meaning,
                idiomLanguageTag = flashcardEntity.idiomLanguageTag,
            )
        }

        return dataParser.encode(DataTransferV2(cards = cards))
    }

    suspend fun import(json: String) {
        dataParser.parseData(json)
            .cards
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
