package com.memorati.feature.cards.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.memorati.feature.cards.CardsRoute

const val CARDS_ROUTE = "cards"
fun NavController.navigateToCards(navOptions: NavOptions) {
    navigate(CARDS_ROUTE, navOptions)
}

fun NavGraphBuilder.cardsScreen() {
    composable(CARDS_ROUTE) {
        CardsRoute()
    }
}
