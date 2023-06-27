package com.memorati

import android.app.Application
import com.memorati.core.common.ForegroundFlow
import com.memorati.core.common.di.ApplicationScope
import com.memorati.feature.assistant.worker.AssistantScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltAndroidApp
class MemoratiApp : Application() {

    @Inject
    lateinit var assistantScheduler: AssistantScheduler

    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var foregroundFlow: ForegroundFlow

    override fun onCreate() {
        super.onCreate()

        foregroundFlow.inForeground.launchIn(scope)
        assistantScheduler.schedule()
    }
}
