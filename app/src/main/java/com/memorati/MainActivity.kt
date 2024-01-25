package com.memorati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.assistant.navigation.assistantScreen
import com.memorati.feature.cards.navigation.cardsScreen
import com.memorati.feature.creation.navigation.cardCreationScreen
import com.memorati.feature.creation.navigation.navigateToCardCreation
import com.memorati.feature.favourites.navigation.favouritesScreen
import com.memorati.feature.quiz.navigation.quizGraph
import com.memorati.feature.settings.navigation.navigateToSettings
import com.memorati.feature.settings.navigation.settingsScreen
import com.memorati.navigation.TopDestination
import com.memorati.ui.NavigationItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val viewModel: MainActivityViewModel = hiltViewModel()
            val destinations by viewModel.state.collectAsStateWithLifecycle()
            MemoratiTheme {
                Scaffold(
                    bottomBar = {
                        NavigationItems(
                            destinations = destinations,
                            currentDestination = currentDestination,
                            navController = navController,
                        )
                    },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues = paddingValues),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = TopDestination.CARDS.route,
                        ) {
                            cardsScreen(
                                onAddCard = { navController.navigateToCardCreation() },
                                onEdit = { flashcard ->
                                    navController.navigateToCardCreation(flashcard.id)
                                },

                                openSettings = {
                                    navController.navigateToSettings()
                                },

                            )
                            cardCreationScreen {
                                navController.navigateUp()
                            }
                            favouritesScreen()
                            assistantScreen()
                            settingsScreen(appVersion = BuildConfig.VERSION_NAME) {
                                navController.navigateUp()
                            }
                            quizGraph(navController)
                        }
                    }
                }
            }
        }
    }
}
