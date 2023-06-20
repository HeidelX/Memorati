package com.memorati.feature.assistant.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.memorati.feature.assistant.AssistantRoute

const val ASSISTANT_ROUTE = "assistant"
internal const val URI_PATTERN = "https://memorati.com/assistant"
fun NavController.navigateToAssistant(navOptions: NavOptions) {
    navigate(ASSISTANT_ROUTE, navOptions)
}

fun NavGraphBuilder.assistantScreen() {
    composable(
        route = ASSISTANT_ROUTE,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = URI_PATTERN
            }
        )
    ) {
        AssistantRoute()
    }
}
