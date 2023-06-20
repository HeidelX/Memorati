package com.memorati

import android.app.Application
import com.memorati.feature.assistant.worker.AssistantScheduler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MemoratiApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AssistantScheduler.schedule(this)
    }
}
