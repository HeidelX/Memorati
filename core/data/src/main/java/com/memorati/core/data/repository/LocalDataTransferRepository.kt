package com.memorati.core.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.memorati.core.common.dispatcher.Dispatcher
import com.memorati.core.common.dispatcher.MemoratiDispatchers.IO
import com.memorati.core.db.transfer.file.DataTransferSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class LocalDataTransferRepository @Inject constructor(
    private val dataTransferSource: DataTransferSource,
    @ApplicationContext private val context: Context,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher,
) : DataTransferRepository {
    override suspend fun export(): File = withContext(dispatcher) {
        val directory = File(context.cacheDir, "export").also {
            it.mkdirs()
        }

        File(directory, "flashcards.json").apply {
            createNewFile()
            bufferedWriter().use { writer ->
                writer.write(dataTransferSource.flashcardsJson())
            }
        }
    }

    override suspend fun import(uri: String) {
        withContext(dispatcher) {
            context.contentResolver.openInputStream(uri.toUri()).use { stream ->
                stream?.bufferedReader()?.use {
                    dataTransferSource.import(it.readText())
                }
            }
        }
    }
}
