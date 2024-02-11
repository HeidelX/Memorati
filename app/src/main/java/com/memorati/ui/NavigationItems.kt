package com.memorati.ui

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.memorati.NavBarItem
import com.memorati.navigation.navigateToTopDestination

@Composable
fun NavigationItems(
    destinations: List<NavBarItem>,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    NavigationBar {
        destinations.forEach { navItem ->
            val selected = currentDestination?.hierarchy?.any { navDest ->
                navDest.route == navItem.topDestination.route
            } == true

            NavigationBarItem(
                icon = {
                    if (navItem.showBadge) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                ) { }
                            },
                        ) {
                            Icon(
                                imageVector = navItem.topDestination.icon,
                                contentDescription = stringResource(navItem.topDestination.iconDescriptionId),
                            )
                        }
                    } else {
                        Icon(
                            imageVector = navItem.topDestination.icon,
                            contentDescription = stringResource(navItem.topDestination.iconDescriptionId),
                        )
                    }
                },
                label = { Text(text = stringResource(navItem.topDestination.labelId)) },
                selected = selected,
                onClick = {
                    navController.navigateToTopDestination(navItem.topDestination)
                },
            )
        }
    }
}
