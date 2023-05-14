package com.memorati.core.data.di

import com.memorati.core.data.repository.FlashcardsRepository
import com.memorati.core.data.repository.LocalFlashcardsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun flashcardsRepository(
        repository: LocalFlashcardsRepository,
    ): FlashcardsRepository
}
