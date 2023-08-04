package com.memorati.core.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.memorati.core.db.transfer.file.DataTransferSource
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

    override suspend fun import(uri: String) {
        context.contentResolver.openInputStream(uri.toUri()).use { stream ->
            stream?.bufferedReader()?.use {
                dataTransferSource.import(it.readText())
            }
        }
    }
}
