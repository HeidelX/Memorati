package com.memorati.feature.assistant.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.memorati.feature.assistant.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssistantNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManagerCompat: NotificationManagerCompat,
) {
    fun notify() {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.memorati.core.design.R.drawable.ic_neurology)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManagerCompat.areNotificationsEnabled()

        if (ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            notificationManagerCompat.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val CHANNEL_ID = "Assistant.Notifications"
        private const val NOTIFICATION_ID = 0x11DDFF
    }
}
