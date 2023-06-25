package com.memorati.feature.assistant.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.memorati.core.common.dispatcher.Dispatcher
import com.memorati.core.common.dispatcher.MemoratiDispatchers.IO
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.feature.assistant.notification.AssistantNotifier
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@HiltWorker
class AssistantWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val flashcardsRepository: FlashcardsRepository,
    private val assistantNotifier: AssistantNotifier,
    private val preferencesDataSource: PreferencesDataSource,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
        val userData = preferencesDataSource.userData.first()

        Log.d(NAME, "time=%s ,userData=%s".format(time, userData))

        val isEligibleTime = time in userData.endTime..userData.startTime
        val hasReviews = flashcardsRepository.flashcardsToReview(
            time = Clock.System.now(),
        ).isNotEmpty()

        if (hasReviews && isEligibleTime) assistantNotifier.notifyUser()
        Result.success()
    }

    companion object {
        const val NAME = "AssistantWorker"
    }
}
