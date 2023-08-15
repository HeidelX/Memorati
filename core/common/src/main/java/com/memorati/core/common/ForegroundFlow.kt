package com.memorati.core.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForegroundFlow @Inject constructor() {

    val inForeground: Flow<Boolean> by lazy {
        MutableStateFlow(false).apply {
            val foregroundStateUpdater = object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) = update { true }
                override fun onStop(owner: LifecycleOwner) = update { false }
            }
            ProcessLifecycleOwner.get().lifecycle.addObserver(foregroundStateUpdater)
        }
    }
}
