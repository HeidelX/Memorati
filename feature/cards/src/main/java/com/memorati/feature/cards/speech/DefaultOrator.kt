package com.memorati.feature.cards.speech

import android.speech.tts.TextToSpeech
import androidx.core.os.bundleOf
import java.util.Locale
import javax.inject.Inject

class DefaultOrator @Inject constructor(
    private val textToSpeech: TextToSpeech,
) : Orator {
    override fun setLanguage(language: String) {
        textToSpeech.setLanguage(Locale(language))
    }

    override fun pronounce(word: String) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, bundleOf(), word)
    }
}
