package com.memorati.core.file

import com.memorati.core.db.dao.FlashcardsDao
import com.memorati.core.db.model.DataTransfer
import com.memorati.core.db.model.TransferableCard
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

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
            DataTransfer(
                cards = transferableCards,
            ),
        )
    }
}
