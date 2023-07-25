package com.memorati.feature.cards

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.memorati.core.model.Flashcard
import com.memorati.core.model.Topic
import com.memorati.core.testing.ComposeActivity
import com.memorati.core.ui.theme.MemoratiTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.hours

class CardsListTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComposeActivity>()

    @Test
    fun openCardsList() {
        composeRule.setContent {
            MemoratiTheme {
                CardsScreen(
                    state = CardsState(
                        map = mapOf(
                            LocalDate(2023, 12, 10) to listOf(
                                Flashcard(
                                    id = 1,
                                    idiom = "Hello",
                                    meaning = "Hallo",
                                    createdAt = Clock.System.now(),
                                    lastReviewAt = Clock.System.now(),
                                    nextReviewAt = Clock.System.now().plus(1.hours),
                                    topics = listOf(
                                        Topic(1, "de"),
                                        Topic(2, "A1"),
                                        Topic(3, "A2"),
                                    ),
                                    idiomLanguageTag = "de",
                                ),
                            ),
                        ),
                        query = "kom",
                    ),
                    speak = {},
                    onEdit = {},
                    onDelete = {},
                    onAddCard = {},
                    openSettings = {},
                    onQueryChange = {},
                    toggleFavoured = {},
                )
            }
        }

        Thread.sleep(10_000)
    }
}
