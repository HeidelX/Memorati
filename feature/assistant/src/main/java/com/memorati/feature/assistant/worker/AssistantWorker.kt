package com.memorati.feature.assistant.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.memorati.core.common.ForegroundFlow
import com.memorati.core.common.dispatcher.Dispatcher
import com.memorati.core.common.dispatcher.MemoratiDispatchers.IO
import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.feature.assistant.algorthim.inRange
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
    private val foregroundFlow: ForegroundFlow,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        when {
            foregroundFlow.inForeground.first() -> {
                Log.d(NAME, "App is in foreground => Don't notify")
                Result.success()
            }

            else -> {
                val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
                val userData = preferencesDataSource.userData.first()
                val isNotQuietTime = !inRange(
                    time = time,
                    startTime = userData.startTime,
                    endTime = userData.endTime,
                )
                Log.d(NAME, "time=%s ,userData=%s".format(time, userData))
                Log.d(NAME, "isNotQuietTime=$isNotQuietTime")
                val hasReviews = flashcardsRepository.flashcardsToReview(
                    time = Clock.System.now(),
                ).first().isNotEmpty()

                if (hasReviews && isNotQuietTime) {
                    Log.d(NAME, "notifyUser")
                    assistantNotifier.notifyUser()
                }
                Result.success()
            }
        }
    }

    companion object {
        const val NAME = "AssistantWorker"
    }
}
