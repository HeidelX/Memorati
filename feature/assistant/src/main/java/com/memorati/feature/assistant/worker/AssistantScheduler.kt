package com.memorati.feature.assistant.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration

object AssistantScheduler {

    fun schedule(context: Context) {
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                AssistantWorker.NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                PeriodicWorkRequestBuilder<DelegatingWorker>(
                    repeatInterval = Duration.ofHours(2),
                ).setInputData(AssistantWorker::class.delegatedData()).build(),
            )
    }
}
