package com.memorati.feature.assistant.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.memorati.feature.assistant.AssistantRoute

const val ASSISTANT_ROUTE = "assistant"
fun NavController.navigateToAssistant(navOptions: NavOptions) {
    navigate(ASSISTANT_ROUTE, navOptions)
}

fun NavGraphBuilder.assistantScreen() {
    composable(ASSISTANT_ROUTE) {
        AssistantRoute()
    }
}
