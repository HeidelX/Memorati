package com.memorati.feature.cards.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.memorati.core.model.Flashcard
import com.memorati.feature.cards.CardsRoute

const val CARDS_ROUTE = "cards"
fun NavController.navigateToCards(navOptions: NavOptions) {
    navigate(CARDS_ROUTE, navOptions)
}

fun NavGraphBuilder.cardsScreen(
    onAddCard: () -> Unit,
    onEdit: (Flashcard) -> Unit,
) {
    composable(CARDS_ROUTE) {
        CardsRoute(onAddCard = onAddCard, onEdit = onEdit)
    }
}
