package com.memorati.feature.creation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.memorati.feature.creation.CardCreationRoute

const val CREATION_ROUTE = "card_creation"
fun NavController.navigateToCardCreation() {
    navigate(CREATION_ROUTE)
}

fun NavGraphBuilder.cardCreationScreen(
    onCardCreated: () -> Unit,
) {
    composable(CREATION_ROUTE) {
        CardCreationRoute(onCardCreated = onCardCreated)
    }
}
