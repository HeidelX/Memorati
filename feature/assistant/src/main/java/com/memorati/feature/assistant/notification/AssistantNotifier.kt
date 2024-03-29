package com.memorati.feature.assistant.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.memorati.feature.assistant.R
import com.memorati.feature.assistant.navigation.URI_PATTERN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssistantNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManagerCompat: NotificationManagerCompat,
) {
    fun notifyUser() {
        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(Intent(Intent.ACTION_VIEW, URI_PATTERN.toUri()))
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannelCompat.Builder(CHANNEL_ID, IMPORTANCE_DEFAULT)
                .setName(context.getString(R.string.assistant_channel_name))
                .setDescription(context.getString(R.string.assistant_channel_description))
                .build()

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.memorati.core.design.R.drawable.ic_neurology)
            .setContentTitle(
                context.getString(com.memorati.core.common.R.string.notification_title),
            )
            .setContentText(context.getString(R.string.notification_content))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManagerCompat.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val CHANNEL_ID = "Assistant_Notifications"
        private const val NOTIFICATION_ID = 0x11DDFF
    }
}
