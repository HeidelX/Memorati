package com.memorati.di

import android.content.Context
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.memorati.BuildConfig
import com.memorati.core.common.di.AppId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @AppId
    fun appId(): String = BuildConfig.APPLICATION_ID

    @Provides
    fun reviewManager(
        @ApplicationContext context: Context,
    ): ReviewManager = ReviewManagerFactory.create(context)
}
