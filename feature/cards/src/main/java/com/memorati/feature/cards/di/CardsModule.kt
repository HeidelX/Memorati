package com.memorati.feature.cards.di

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CardsModule {

    @Provides
    @Singleton
    fun tts(@ApplicationContext context: Context): TextToSpeech {
        return TextToSpeech(context) { status ->
            Log.d("TextToSpeech", "$status")
        }
    }
}
