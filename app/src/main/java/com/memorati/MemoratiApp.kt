package com.memorati

import android.app.Application
import com.memorati.feature.assistant.worker.AssistantScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MemoratiApp : Application() {

    @Inject
    lateinit var assistantScheduler: AssistantScheduler

    override fun onCreate() {
        super.onCreate()
        assistantScheduler.schedule()
    }
}
