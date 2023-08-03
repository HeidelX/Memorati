package com.memorati.feature.cards.di

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.memorati.feature.cards.speech.DefaultOrator
import com.memorati.feature.cards.speech.Orator
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
    fun tts(@ApplicationContext context: Context): Orator {
        return DefaultOrator(
            TextToSpeech(context) { status ->
                Log.d("TextToSpeech", "$status")
            },
        )
    }
}
