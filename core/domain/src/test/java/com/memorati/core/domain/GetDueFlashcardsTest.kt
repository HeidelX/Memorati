package com.memorati.core.domain

import com.memorati.core.testing.repository.FakeFlashcardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


class GetDueFlashcardsTest {

    @Test
    fun `Cards with low "consecutiveCorrectCount" has top priority`() = runTest {

    }

    @Test
    fun `Cards with older "lastReviewAt" has top priority`() = runTest {

    }

    @Test
    fun `Cards with due "nextReviewAt" has top priority`() = runTest {

    }

    @Test
    fun `30 Cards are returned per review`() = runTest {

    }
}