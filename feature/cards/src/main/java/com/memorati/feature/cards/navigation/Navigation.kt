package com.memorati.feature.cards.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.memorati.feature.cards.CardsRoute

const val CARDS_ROUTE = "cards"
fun NavController.navigateToCards() {
    navigate(CARDS_ROUTE)
}

fun NavGraphBuilder.cardsScreen() {
    composable(CARDS_ROUTE) {
        CardsRoute()
    }
}
