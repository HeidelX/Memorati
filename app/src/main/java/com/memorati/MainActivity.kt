package com.memorati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.review.ReviewManager
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
import com.memorati.ui.navigationSuiteItems
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var reviewManager: ReviewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askUserForReview()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val viewModel: MainActivityViewModel = hiltViewModel()
            val destinations by viewModel.state.collectAsStateWithLifecycle()
            MemoratiTheme {
                NavigationSuiteScaffold(
                    navigationSuiteItems = {
                        navigationSuiteItems(
                            destinations,
                            currentDestination,
                            navController,
                        )
                    },
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

    private fun askUserForReview() {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        // Only trigger it on updates and not on first install
        if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
            val request = reviewManager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) reviewManager.launchReviewFlow(this, task.result)
            }
        }
    }
}
