package com.memorati.core.db.di

import android.content.Context
import androidx.room.Room
import com.memorati.core.db.MemoratiDatabase
import com.memorati.core.db.dao.FlashcardsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    @Singleton
    fun providesMemoratiDatabase(
        @ApplicationContext context: Context,
    ): MemoratiDatabase = Room.databaseBuilder(
        context,
        MemoratiDatabase::class.java,
        "memorati-database",
    ).build()

    @Provides
    fun flashcardsDao(
        database: MemoratiDatabase,
    ): FlashcardsDao = database.flashCardsDao()
}
