package com.memorati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ViewAgenda
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memorati.feature.cards.CardsRoute
import com.memorati.feature.cards.creation.CardCreationRoute
import com.memorati.ui.theme.MemoratiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MemoratiTheme {
                Scaffold(
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FabButton {
                            navController.navigate("card-creation")
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.ViewAgenda,
                                        contentDescription = ""
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = false,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Favorite,
                                        contentDescription = ""
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = false ,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Settings,
                                        contentDescription = ""
                                    )
                                }
                            )
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "cards",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("cards") {
                                CardsRoute()
                            }
                            composable("card-creation") {
                                CardCreationRoute {
                                    navController.navigateUp()
                                }
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
