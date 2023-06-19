package com.memorati.feature.assistant.algorthim

import com.memorati.core.model.AdditionalInfo
import com.memorati.core.model.Flashcard
import kotlinx.datetime.Clock
import kotlin.math.ln
import kotlin.time.Duration.Companion.minutes

fun Flashcard.scheduleNextReview(): Flashcard {
    val adaptiveInterval = calculateAdaptiveInterval(
        additionalInfo.difficulty,
        additionalInfo.consecutiveCorrectCount,
    )
    val timeWeightedInterval =
        calculateTimeWeightedInterval(additionalInfo.memoryStrength)

    // Choose the shorter interval between adaptive and time-weighted intervals.
    val nextReviewInterval = minOf(adaptiveInterval, timeWeightedInterval).minutes

    // Calculate the next review date by adding the interval to the last review date.
    return copy(
        nextReviewAt = lastReviewAt.plus(nextReviewInterval),
    )
}

fun Flashcard.handleReviewResponse(
    isCorrect: Boolean,
): Flashcard {
    val (difficulty, consecutiveCorrectCount) = if (isCorrect) {
        // Increase difficulty for correct responses
        additionalInfo.difficulty * 1.1 to (additionalInfo.consecutiveCorrectCount + 1)
    } else {
        // Decrease difficulty for incorrect responses
        additionalInfo.difficulty * 0.9 to 0
    }
    return copy(
        additionalInfo = AdditionalInfo(
            difficulty = difficulty,
            consecutiveCorrectCount = consecutiveCorrectCount,
            // Apply decay to memory strength over time
            memoryStrength = additionalInfo.memoryStrength * 0.95,
        ),
        lastReviewAt = Clock.System.now(),
    )
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
