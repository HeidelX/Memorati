package com.memorati.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.memorati.feature.settings.SettingsRoute

const val SETTINGS_ROUTE = "settings"
fun NavController.navigateToSettings() {
    navigate(SETTINGS_ROUTE)
}

fun NavGraphBuilder.settingsScreen(
    appVersion: String,
    onBack: () -> Unit,
) {
    composable(
        route = SETTINGS_ROUTE,
    ) {
        SettingsRoute(
            appVersion = appVersion,
            onBack = onBack,
        )
    }
}
