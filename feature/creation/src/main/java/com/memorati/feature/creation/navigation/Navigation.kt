package com.memorati.feature.creation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.memorati.feature.creation.CardCreationRoute

internal const val CARD_ID = "cardId"
const val CREATION_ROUTE = "card_creation/{$CARD_ID}"
fun NavController.navigateToCardCreation(cardId: Long = -1) {
    navigate("card_creation/$cardId")
}

fun NavGraphBuilder.cardCreationScreen(
    onBack: () -> Unit,
) {
    composable(
        route = CREATION_ROUTE,
        arguments = listOf(
            navArgument(CARD_ID) {
                type = NavType.LongType
                defaultValue = -1
            },
        ),
    ) {
        CardCreationRoute(onBack = onBack)
    }
}
