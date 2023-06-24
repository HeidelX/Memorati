package com.memorati.di

import com.memorati.BuildConfig
import com.memorati.core.common.di.AppId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @AppId
    fun appId(): String = BuildConfig.APPLICATION_ID
}
