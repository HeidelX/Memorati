package com.memorati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ViewAgenda
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.memorati.feature.cards.CardsRoute
import com.memorati.feature.cards.creation.CardCreationRoute
import com.memorati.feature.cards.favourties.FavouritesRoute
import com.memorati.ui.theme.MemoratiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            MemoratiTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            actions = {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings" )
                            }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FabButton {
                            navController.navigate("card-creation")
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == "cards" } == true,
                                onClick = { navController.navigate("cards") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.ViewAgenda,
                                        contentDescription = "",
                                    )
                                },
                            )

                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == "favourites" } == true,
                                onClick = { navController.navigate("favourites") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Favorite,
                                        contentDescription = "",
                                    )
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
                            composable("cards") {
                                CardsRoute()
                            }

                            composable("card-creation") {
                                CardCreationRoute {
                                    navController.navigateUp()
                                }
                            }

                            composable("favourites") {
                                FavouritesRoute()
                            }
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
            Icons.Rounded.Add,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Add")
    }
}
