package com.memorati.core.data.repository

import java.io.File

interface DataTransferRepository {
    /**
     * returns file to share of exported flashcards
     * might throw an error if file could not be created
     */
    suspend fun export(): File
}
