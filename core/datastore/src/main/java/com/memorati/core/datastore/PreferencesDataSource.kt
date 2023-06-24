package com.memorati.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.memorati.core.model.UserData
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data.map {
        UserData(
            startTime = it.startTime,
            endTime = it.endTime,
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
}
