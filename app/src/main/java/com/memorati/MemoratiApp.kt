package com.memorati

import android.app.Application
import com.memorati.feature.assistant.notification.AssistantNotifier
import com.memorati.feature.assistant.worker.AssistantScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MemoratiApp : Application() {

    @Inject lateinit var assistantNotifier: AssistantNotifier

    override fun onCreate() {
        super.onCreate()

        AssistantScheduler.schedule(this)

        assistantNotifier.notifyUser()
    }
}
