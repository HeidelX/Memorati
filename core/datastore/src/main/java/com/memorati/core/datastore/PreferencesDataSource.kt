package com.memorati.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.memorati.core.model.UserData
import com.memorati.core.model.UserData.Companion.COUNT
import com.memorati.core.model.UserData.Companion.END
import com.memorati.core.model.UserData.Companion.INTERVAL
import com.memorati.core.model.UserData.Companion.START
import com.memorati.core.model.UserData.Companion.WEEKS
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime
import java.io.IOException
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data.map { prefs ->
        with(prefs) {
            UserData(
                idiomLanguageTag = idiomLanguageTag,
                weeksOfReview = if (weeksOfReview == 0) WEEKS else weeksOfReview,
                endTime = if (endTime == 0) END else LocalTime.fromMillisecondOfDay(endTime),
                startTime = if (startTime == 0) START else LocalTime.fromMillisecondOfDay(startTime),
                reminderInterval = if (alarmInterval == 0L) INTERVAL else alarmInterval.milliseconds,
                wordCorrectnessCount = if (wordCorrectnessCount == 0) COUNT else wordCorrectnessCount,
            )
        }
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

    suspend fun setIdiomLanguageTag(tag: String) {
        try {
            userPreferences.updateData {
                it.copy {
                    idiomLanguageTag = tag
                }
            }
        } catch (ioException: IOException) {
            Log.e("PreferencesDataSource", "Failed to update user preferences", ioException)
        }
    }
}
