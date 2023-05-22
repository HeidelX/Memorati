package com.memorati

import MemoratiIcons
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.memorati.core.design.component.MemoratiTopAppBar
import com.memorati.feature.assistant.navigation.assistantScreen
import com.memorati.feature.assistant.navigation.navigateToAssistant
import com.memorati.feature.cards.navigation.cardsScreen
import com.memorati.feature.cards.navigation.navigateToCards
import com.memorati.feature.creation.navigation.cardCreationScreen
import com.memorati.feature.creation.navigation.navigateToCardCreation
import com.memorati.feature.favourites.navigation.favouritesScreen
import com.memorati.feature.favourites.navigation.navigateToFavourites
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
                        MemoratiTopAppBar(scrollBehavior = scrollBehavior)
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FabButton {
                            navController.navigateToCardCreation()
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == "cards" } == true,
                                onClick = { navController.navigateToCards() },
                                icon = {
                                    Icon(
                                        imageVector = MemoratiIcons.Cards,
                                        contentDescription = "",
                                    )
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.cards))
                                },
                            )

                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == "favourites" } == true,
                                onClick = { navController.navigateToFavourites() },
                                icon = {
                                    Icon(
                                        imageVector = MemoratiIcons.Favourites,
                                        contentDescription = "",
                                    )
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.favourites))
                                },
                            )

                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == "Assistant" } == true,
                                onClick = { navController.navigateToAssistant() },
                                icon = {
                                    Icon(
                                        imageVector = MemoratiIcons.Assistant,
                                        contentDescription = "",
                                    )
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.assistant))
                                },
                            )
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "cards",
                            modifier = Modifier.padding(innerPadding),
                        ) {
                            cardsScreen()
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
fun FabButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClickAction() },
    ) {
        Icon(
            MemoratiIcons.Add,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Add")
    }
}
