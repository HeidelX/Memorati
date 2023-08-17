package com.memorati.ui

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.memorati.NavBarItem
import com.memorati.navigation.TopDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoratiNavBar(
    currentDestination: NavDestination?,
    destinations: List<NavBarItem>,
    onClickAction: (TopDestination) -> Unit,
) {
    NavigationBar {
        destinations.forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { navDest ->
                    navDest.route == navItem.topDestination.route
                } == true,
                onClick = {
                    onClickAction(navItem.topDestination)
                },
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
                label = {
                    Text(text = stringResource(navItem.topDestination.labelId))
                },
            )
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    MemoratiNavBar(currentDestination = null, destinations = TopDestination.toNavItems()) {}
}
