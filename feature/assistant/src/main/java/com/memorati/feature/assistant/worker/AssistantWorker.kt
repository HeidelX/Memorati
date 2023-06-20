package com.memorati.feature.assistant.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.memorati.core.common.dispatcher.Dispatcher
import com.memorati.core.common.dispatcher.MemoratiDispatchers.IO
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.feature.assistant.notification.AssistantNotifier
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

@HiltWorker
class AssistantWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val flashcardsRepository: FlashcardsRepository,
    private val assistantNotifier: AssistantNotifier,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val hasReviews = flashcardsRepository.flashcardsToReview(time = Clock.System.now())
            .first()
            .isNotEmpty()

        if (hasReviews) assistantNotifier.notifyUser()
        Result.success()
    }

    companion object {
        const val NAME = "AssistantWorker"
    }
}
