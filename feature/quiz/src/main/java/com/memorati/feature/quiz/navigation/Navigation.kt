package com.memorati.feature.quiz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.memorati.feature.quiz.QuizRoute

const val Quiz_ROUTE = "quiz"
fun NavController.navigateToQuiz(navOptions: NavOptions) {
    navigate(Quiz_ROUTE, navOptions)
}

fun NavGraphBuilder.quizScreen() {
    composable(Quiz_ROUTE) {
        QuizRoute()
    }
}
