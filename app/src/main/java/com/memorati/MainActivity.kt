package com.memorati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.memorati.core.design.component.MemoratiTopAppBar
import com.memorati.feature.assistant.navigation.assistantScreen
import com.memorati.feature.cards.navigation.cardsScreen
import com.memorati.feature.creation.navigation.cardCreationScreen
import com.memorati.feature.creation.navigation.navigateToCardCreation
import com.memorati.feature.favourites.navigation.favouritesScreen
import com.memorati.navigation.TopDestination
import com.memorati.navigation.navigateToTopDestination
import com.memorati.ui.MemoratiNanBar
import com.memorati.ui.theme.MemoratiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scrollBehavior =
                TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            MemoratiTheme {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        AnimatedVisibility(
                            visible = shouldShowTopBar(currentDestination),
                            enter = slideInVertically(initialOffsetY = { -it }),
                            exit = slideOutVertically(targetOffsetY = { -it }),
                        ) {
                            MemoratiTopAppBar(scrollBehavior = scrollBehavior)
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    bottomBar = {
                        AnimatedVisibility(
                            visible = shouldShowTopBar(currentDestination),
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it }),

                        ) {
                            MemoratiNanBar(currentDestination) { topDest ->
                                navController.navigateToTopDestination(topDest)
                            }
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = TopDestination.CARDS.route,
                            modifier = Modifier.padding(innerPadding),
                        ) {
                            cardsScreen(
                                onAddCard = { navController.navigateToCardCreation() },
                                onEdit = { flashcard ->
                                    navController.navigateToCardCreation(flashcard.id)
                                },
                            )
                            cardCreationScreen {
                                navController.navigateUp()
                            }
                            favouritesScreen()
                            assistantScreen()
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun shouldShowTopBar(currentDestination: NavDestination?): Boolean {
    return currentDestination?.hierarchy?.any { navDest ->
        navDest.route in TopDestination.values().map { it.route }
    } == true
}
