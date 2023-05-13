package com.memorati.core.db

import android.app.Application
import com.memorati.core.db.model.Flashcard
import kotlinx.coroutines.flow.Flow

class CardsRepo {

    fun cards(application: Application): Flow<List<Flashcard>> {
        return memoratiDb(application).flashCardsDao().getAll()
    }
}
