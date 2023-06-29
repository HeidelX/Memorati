package com.memorati.core.common.permission

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

fun Context.openNotificationsSettings() {
    val intent = Intent().apply {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }

            else -> {
                action = "android.settings.APP_NOTIFICATION_SETTINGS"
                putExtra("app_package", packageName)
                putExtra("app_uid", applicationInfo.uid)
            }
        }
    }
    startActivity(intent)
}
