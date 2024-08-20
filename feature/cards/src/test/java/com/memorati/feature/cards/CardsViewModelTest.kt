package com.memorati.feature.cards

import com.memorati.core.datastore.PreferencesDataSource
import com.memorati.core.model.UserData
import com.memorati.core.testing.repository.TestFlashcardsRepository
import com.memorati.core.testing.rule.MainDispatcherRule
import com.memorati.feature.cards.speech.Orator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CardsViewModelTest {

    private lateinit var cardsViewModel: CardsViewModel
    private val testOrator = object : Orator {
        var languageTag: String = ""
        var word: String = ""
        override fun setLanguage(language: String) {
            languageTag = language
        }

        override fun pronounce(word: String) {
            this.word = word
        }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeTest
    fun setup() {
        cardsViewModel = CardsViewModel(
            orator = testOrator,
            flashcardsRepository = TestFlashcardsRepository(),
            userPreferences = FakeUserPreferences(),
        )
    }

    @Test
    fun `Assert that flashcards are grouped and sorted by date in a descending order`() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        cardsViewModel.state.value.map.keys.zipWithNext { a, b ->
            assertTrue { a >= b }
        }

        job.cancel()
    }

    @Test
    fun `Assert that flashcards are sorted by idiom`() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        cardsViewModel.state.value.map.values.forEach { cards ->
            cards.zipWithNext { a, b ->
                assertTrue { a.idiom <= b.idiom }
            }
        }

        job.cancel()
    }

    @Test
    fun toggleFavoured() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        val card = cardsViewModel.state.value.map.values.first()[0]
        // Set favoured to true
        cardsViewModel.toggleFavoured(card)
        val flashcard = cardsViewModel.state.value.map.values.first()[0]
        assertTrue { flashcard.favoured }
        // Set favoured to false
        cardsViewModel.toggleFavoured(flashcard)
        assertFalse { cardsViewModel.state.value.map.values.first()[0].favoured }
        job.cancel()
    }

    @Test
    fun deleteCard() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        val cardToDelete = cardsViewModel.state.value.map.values.first()[0]
        cardsViewModel.deleteCard(cardToDelete)
        assertFalse { cardsViewModel.state.value.map.values.flatten().contains(cardToDelete) }
        job.cancel()
    }

    @Test
    fun `Query filters flashcards when idiom contains it`() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        cardsViewModel.onQueryChange("200")

        val flashcards = cardsViewModel.state.value.map.values.flatten()
        assertTrue { flashcards.size == 1 }
        flashcards.forEach { card ->
            assertTrue { card.idiom.contains("200") }
        }
        job.cancel()
    }

    @Test
    fun `Query filters flashcards when meaning contains it`() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        cardsViewModel.onQueryChange("-200")

        val flashcards = cardsViewModel.state.value.map.values.flatten()
        assertTrue { flashcards.size == 1 }
        flashcards.forEach { card ->
            assertTrue { card.meaning.contains("-200") }
        }
        job.cancel()
    }

    @Test
    fun speak() = runTest {
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            cardsViewModel.state.collect()
        }
        assertTrue { cardsViewModel.state.value.map.isNotEmpty() }
        val card = cardsViewModel.state.value.map.values.first()[0]
        cardsViewModel.speak(card)

        assertTrue { card.idiomLanguageTag == testOrator.languageTag }
        assertTrue { card.idiom == testOrator.word }
        job.cancel()
    }
}

class FakeUserPreferences() : PreferencesDataSource {
    override val userData: Flow<UserData>
        get() = flowOf(UserData())

    override suspend fun setStartTime(time: Int) {
    }

    override suspend fun setEndTime(time: Int) {
    }

    override suspend fun setAlarmInterval(interval: Long) {
    }

    override suspend fun setIdiomLanguageTag(tag: String) {
    }

    override suspend fun setWeekCountOfReview(count: Int) {
    }

    override suspend fun setCorrectnessCount(count: Int) {
    }
}
