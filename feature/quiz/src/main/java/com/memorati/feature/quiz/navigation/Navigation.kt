package com.memorati.feature.quiz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.memorati.feature.quiz.QuizRoute
import com.memorati.feature.quiz.knowledge.KnowledgeDirectionsRoute
import com.memorati.feature.quiz.matching.MatchingRoute
import com.memorati.feature.quiz.typing.TypingRoute

const val QUIZ_ROUTE = "quiz_route"
internal const val QUIZ = "quiz"
internal const val KNOWLEDGE_DIRECTIONS = "knowledge-directions"
internal const val MATCHING = "matching"
internal const val TYPING = "typing"
fun NavController.navigateToQuiz(navOptions: NavOptions) {
    navigate(QUIZ_ROUTE, navOptions)
}

fun NavGraphBuilder.quizGraph(navController: NavController) {
    // Nested graph
    navigation(startDestination = QUIZ, route = QUIZ_ROUTE) {
        composable(QUIZ) {
            QuizRoute(
                openTyping = { navController.navigate(TYPING) },
                openMatching = { navController.navigate(MATCHING) },
                openKnowledgeDirections = { navController.navigate(KNOWLEDGE_DIRECTIONS) },
            )
        }

        composable(KNOWLEDGE_DIRECTIONS) {
            KnowledgeDirectionsRoute(
                onBack = { navController.navigateUp() },
            )
        }
        composable(MATCHING) {
            MatchingRoute(
                onBack = { navController.navigateUp() },
            )
        }
        composable(TYPING) {
            TypingRoute(
                onBack = { navController.navigateUp() },
            )
        }
    }
}
