package com.memorati.feature.assistant.algorthim

import com.memorati.core.model.AdditionalInfo
import com.memorati.core.model.Flashcard
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.ln
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun scheduleNextReview(flashcard: Flashcard): Instant {
    val adaptiveInterval = calculateAdaptiveInterval(
        flashcard.additionalInfo.difficulty,
        flashcard.additionalInfo.consecutiveCorrectCount,
    )
    val timeWeightedInterval =
        calculateTimeWeightedInterval(flashcard.additionalInfo.memoryStrength)

    // Choose the shorter interval between adaptive and time-weighted intervals.
    val nextReviewInterval =
        minOf(adaptiveInterval, timeWeightedInterval).toDuration(DurationUnit.MILLISECONDS)

    // Calculate the next review date by adding the interval to the last review date.
    return flashcard.additionalInfo.lastReviewTime.plus(nextReviewInterval)
}

fun handleReviewResponse(
    additionalInfo: AdditionalInfo,
    isCorrect: Boolean,
): AdditionalInfo = with(additionalInfo) {
    return if (isCorrect) {
        copy(
            difficulty = difficulty * 1.1, // Increase difficulty for correct responses,
            consecutiveCorrectCount = consecutiveCorrectCount + 1,
            memoryStrength = memoryStrength * 0.95, // Apply decay to memory strength over time
            lastReviewTime = Clock.System.now(),
        )
    } else {
        copy(
            difficulty = difficulty * 0.9, // Decrease difficulty for incorrect responses
            consecutiveCorrectCount = 0,
            memoryStrength = memoryStrength * 0.95, // Apply decay to memory strength over time
            lastReviewTime = Clock.System.now(),
        )
    }
}

private fun calculateAdaptiveInterval(difficulty: Double, consecutiveCorrectCount: Int): Long {
    // Adjust the base interval based on the flashcard's difficulty
    val baseInterval = 1000 // Base interval in milliseconds
    val adjustedInterval = baseInterval * difficulty
    // Apply a multiplier based on the consecutive correct count
    val multiplier = when (consecutiveCorrectCount) {
        0 -> 0.5 // If no consecutive correct answers, decrease the interval by half
        1 -> 1.0 // If 1 consecutive correct answer, maintain the same interval
        else -> 1.5 // If more than 1 consecutive correct answer, increase the interval by 50%
    }
    // Calculate the final adaptive interval by multiplying the adjusted interval with the multiplier
    return (adjustedInterval * multiplier).toLong()
}

private fun calculateTimeWeightedInterval(memoryStrength: Double): Long {
    // Set the base interval based on the desired initial review interval
    val baseInterval = 2000 // Base interval in milliseconds
    // Set the decay factor based on how quickly you want the memory strength to decay over time
    val decayFactor = 0.9 // Adjust this value based on your preference
    // Calculate the time-weighted interval using logarithmic decay
    return (baseInterval * (1 - ln(memoryStrength) / ln(decayFactor))).toLong()
}
