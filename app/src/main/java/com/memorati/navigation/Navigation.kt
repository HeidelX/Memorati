package com.memorati.navigation

import MemoratiIcons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import com.memorati.NavBarItem
import com.memorati.feature.assistant.navigation.ASSISTANT_ROUTE
import com.memorati.feature.assistant.navigation.navigateToAssistant
import com.memorati.feature.cards.navigation.CARDS_ROUTE
import com.memorati.feature.cards.navigation.navigateToCards
import com.memorati.feature.favourites.navigation.FAVOURITES_ROUTE
import com.memorati.feature.favourites.navigation.navigateToFavourites
import com.memorati.feature.quiz.navigation.Quiz_ROUTE
import com.memorati.feature.quiz.navigation.navigateToQuiz
import com.memorati.feature.assistant.R as AssistantR
import com.memorati.feature.cards.R as CardsR
import com.memorati.feature.favourites.R as FavouritesR
import com.memorati.feature.quiz.R as QuizR

enum class TopDestination(
    val route: String,
    val icon: ImageVector,
    val iconDescriptionId: Int,
    val labelId: Int,
) {
    CARDS(
        route = CARDS_ROUTE,
        icon = MemoratiIcons.Cards,
        labelId = CardsR.string.cards,
        iconDescriptionId = CardsR.string.cards,
    ),
    QUIZ(
        route = Quiz_ROUTE,
        icon = MemoratiIcons.Quiz,
        labelId = QuizR.string.quiz,
        iconDescriptionId = QuizR.string.quiz,
    ),
    FAVOURITES(
        route = FAVOURITES_ROUTE,
        icon = MemoratiIcons.Favourites,
        labelId = FavouritesR.string.favourites,
        iconDescriptionId = FavouritesR.string.favourites,
    ),
    ASSISTANT(
        route = ASSISTANT_ROUTE,
        icon = MemoratiIcons.Assistant,
        labelId = AssistantR.string.assistant,
        iconDescriptionId = AssistantR.string.assistant,
    ),
    ;

    companion object {
        fun toNavItems() = values().map { topDestination ->
            NavBarItem(
                topDestination = topDestination,
                showBadge = false,
            )
        }
    }
}

fun NavController.navigateToTopDestination(topDestination: TopDestination) {
    val navOptions = navOptions {
        popUpTo(graph.findStartDestination().id) {
            // TODO saveState = true it seems to be a bug in Navigation compose library
        }
        launchSingleTop = true
        restoreState = true
    }
    when (topDestination) {
        TopDestination.CARDS -> navigateToCards(navOptions)
        TopDestination.FAVOURITES -> navigateToFavourites(navOptions)
        TopDestination.ASSISTANT -> navigateToAssistant(navOptions)
        TopDestination.QUIZ -> navigateToQuiz(navOptions)
    }
}
