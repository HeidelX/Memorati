package com.memorati.feature.favourites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.memorati.feature.favourites.FavouritesRoute

const val FAVOURITES_ROUTE = "favourites"
fun NavController.navigateToFavourites(navOptions: NavOptions) {
    navigate(FAVOURITES_ROUTE, navOptions)
}

fun NavGraphBuilder.favouritesScreen() {
    composable(FAVOURITES_ROUTE) {
        FavouritesRoute()
    }
}
