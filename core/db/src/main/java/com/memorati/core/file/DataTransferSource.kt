package com.memorati.core.file

import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.AdditionalInfoEntity
import com.memorati.core.db.model.DataTransfer
import com.memorati.core.db.model.FlashcardEntity
import com.memorati.core.db.model.TransferableCard
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

class DataTransferSource @Inject constructor(
    private val flashcardsDao: FlashcardsDao,
) {
    suspend fun flashcardsJson(): String {
        val transferableCards = flashcardsDao.allFlashcard().first().map { flashcardEntity ->
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
        dataTransfer.cards.forEach { card ->
            if (flashcardsDao.find(card.idiom, card.description).isEmpty()) {
                flashcardsDao.insert(
                    FlashcardEntity(
                        flashcardId = 0,
                        createdAt = Clock.System.now(),
                        lastReviewAt = Clock.System.now(),
                        nextReviewAt = Clock.System.now().plus(6.hours),
                        front = card.idiom,
                        back = card.description,
                        favoured = false,
                        additionalInfoEntity = AdditionalInfoEntity(),
                    ),
                )
            } // else scape repeated card
        }
    }
}
