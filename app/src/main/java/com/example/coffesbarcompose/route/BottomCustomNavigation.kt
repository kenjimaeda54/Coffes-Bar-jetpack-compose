package com.example.coffesbarcompose.route

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


@Composable
fun BottomCustomNavigation(navHostController: NavHostController, navDestination: NavDestination?) {

    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.tertiaryContainer) {

        BottomScreens.screens().forEach {
            AddItem(
                navController = navHostController,
                screen = it,
                currentDestination = navDestination
            )
        }

    }

}

@Composable
fun RowScope.AddItem(
    navController: NavHostController,
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
) {

    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
           navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                restoreState = true
            }

        },
        icon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = screen.icon),
                contentDescription = "Icon Navigation"
            )
        },
        unselectedContentColor =  MaterialTheme.colorScheme.secondary.copy(0.3f),
        selectedContentColor = MaterialTheme.colorScheme.secondary,
        )

}