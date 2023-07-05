package com.memorati.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.memorati.core.model.UserData
import com.memorati.core.model.UserData.Companion.END
import com.memorati.core.model.UserData.Companion.INTERVAL
import com.memorati.core.model.UserData.Companion.START
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime
import java.io.IOException
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data.map {
        UserData(
            startTime = if (it.startTime == 0) START else LocalTime.fromMillisecondOfDay(it.startTime),
            endTime = if (it.endTime == 0) END else LocalTime.fromMillisecondOfDay(it.endTime),
            reminderInterval = if (it.alarmInterval == 0L) INTERVAL else it.alarmInterval.milliseconds,
            isSpeechEnabled = it.isSpeechEnabled,
        )
    }

    suspend fun setStartTime(time: Int) {
        try {
            userPreferences.updateData {
                it.copy {
                    startTime = time
                }
            }
        } catch (ioException: IOException) {
            Log.e("PreferencesDataSource", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setEndTime(time: Int) {
        try {
            userPreferences.updateData {
                it.copy {
                    endTime = time
                }
            }
        } catch (ioException: IOException) {
            Log.e("PreferencesDataSource", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setAlarmInterval(interval: Long) {
        try {
            userPreferences.updateData {
                it.copy {
                    alarmInterval = interval
                }
            }
        } catch (ioException: IOException) {
            Log.e("PreferencesDataSource", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setSpeechEnabled(enabled: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    isSpeechEnabled = enabled
                }
            }
        } catch (ioException: IOException) {
            Log.e("PreferencesDataSource", "Failed to update user preferences", ioException)
        }
    }
}
