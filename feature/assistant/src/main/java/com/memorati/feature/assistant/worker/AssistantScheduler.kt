package com.memorati.feature.assistant.worker

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.memorati.core.common.di.ApplicationScope
import com.memorati.core.datastore.PreferencesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@Singleton
class AssistantScheduler @Inject constructor(
    preferencesDataSource: PreferencesDataSource,
    @ApplicationScope applicationScope: CoroutineScope,
    @ApplicationContext private val context: Context,
) {

    init {
        preferencesDataSource.userData
            .map { it.reminderInterval }
            .distinctUntilChanged()
            .onEach {
                schedule(it)
            }.launchIn(applicationScope)
    }

    fun schedule(duration: Duration = 15.minutes) {
        Log.d(TAG, "schedule(duration=$duration)")
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                AssistantWorker.NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                PeriodicWorkRequestBuilder<DelegatingWorker>(
                    repeatInterval = duration.toJavaDuration(),
                ).setInputData(AssistantWorker::class.delegatedData()).build(),
            )
    }

    companion object {
        private const val TAG = "AssistantScheduler"
    }
}
