package com.memorati.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.memorati.navigation.TopDestination

@Composable
fun MemoratiNanBar(
    currentDestination: NavDestination?,
    onClickAction: (TopDestination) -> Unit,
) {
    NavigationBar {
        TopDestination.values().forEach { topDest ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { navDest ->
                    navDest.route == topDest.route
                } == true,
                onClick = {
                    onClickAction(topDest)
                },
                icon = {
                    Icon(
                        imageVector = topDest.icon,
                        contentDescription = stringResource(topDest.iconDescriptionId),
                    )
                },
                label = {
                    Text(text = stringResource(topDest.labelId))
                },
            )
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    MemoratiNanBar(currentDestination = null, onClickAction = {})
}
