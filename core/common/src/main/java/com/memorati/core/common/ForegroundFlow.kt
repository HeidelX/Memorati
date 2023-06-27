package com.memorati.core.common

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForegroundFlow @Inject constructor() {

    val inForeground: Flow<Boolean> by lazy {
        MutableStateFlow(false).apply {
            val foregroundStateUpdater = object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    super.onStart(owner)
                    update { true }
                }

                override fun onStop(owner: LifecycleOwner) {
                    super.onStop(owner)
                    update { false }
                }
            }

            val processLifecycle = ProcessLifecycleOwner.get().lifecycle
            processLifecycle.addObserver(foregroundStateUpdater)
        }.onEach {
            Log.d("ForegroundFlow", "inForeground=$it")
        }
    }
}
