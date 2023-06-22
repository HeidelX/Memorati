package com.memorati.core.data.repository

import android.content.Context
import com.memorati.core.file.DataTransferSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LocalDataTransferRepository @Inject constructor(
    private val dataTransferSource: DataTransferSource,
    @ApplicationContext private val context: Context,
) : DataTransferRepository {
    override suspend fun export(): File {
        val directory = File(context.cacheDir, "export").also { it.mkdirs() }
        val file = File(directory, "flashcards.json")
        file.createNewFile()

        file.bufferedWriter().use { writer ->
            writer.write(dataTransferSource.flashcardsJson())
        }

        return file
    }
}