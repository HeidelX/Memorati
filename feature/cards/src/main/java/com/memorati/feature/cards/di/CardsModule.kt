package com.memorati.feature.cards.di

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions
import com.google.mlkit.nl.languageid.LanguageIdentifier
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

    @Provides
    @Singleton
    fun languageIdentifier(): LanguageIdentifier {
        return LanguageIdentification.getClient(
            LanguageIdentificationOptions.Builder()
                .setConfidenceThreshold(0.34f)
                .build()
        )
    }
}