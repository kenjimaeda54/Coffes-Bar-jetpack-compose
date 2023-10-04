package com.example.coffesbarcompose.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffesbarcompose.route.BottomBarScreen
import com.example.coffesbarcompose.route.BottomNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { ButtomBar(navController = navController) }) {
        BottomNavGraph(navController = navController)
    }


}

@Composable
fun ButtomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Favorite,
        BottomBarScreen.Cart
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach {
            AddItem(
                navController,
                currentDestination,
                screen = it,
            )
        }

    }
}

@Composable
fun RowScope.AddItem(
    navController: NavHostController,
    currentDestination: NavDestination?,
    screen: BottomBarScreen
) {

    //usando o popUp caso a pessoa clique no botao inferior de voltar , launchSingleTop possilibita sair do App
    BottomNavigationItem(selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true, onClick = {
        navController.navigate(screen.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }, icon = {
        Icon(
            painter = painterResource(id = screen.icon),
            contentDescription = "Icon bottom navigation"
        )
    })

}