package com.memorati.feature.assistant.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ASSISTANT_ROUTE = "assistant"
fun NavController.navigateToAssistant() {
    navigate(ASSISTANT_ROUTE)
}

fun NavGraphBuilder.assistantScreen() {
    composable(ASSISTANT_ROUTE) {
        Text(text = "Hello, Assistant")
    }
}
