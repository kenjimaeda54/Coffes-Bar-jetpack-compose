package com.example.coffesbarcompose.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffesbarcompose.route.BottomCustomNavigation
import com.example.coffesbarcompose.route.BottomScreens
import com.example.coffesbarcompose.route.NavGraphApp
import com.example.coffesbarcompose.route.StackScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute =
        navBackStackEntry?.destination?.route?.split("/") //porque a rota tem argumento estou fazendo  o split nmo /
    val stringRoutesStack = StackScreens.values().map { it.toString() }
    val stringBottomRoute = BottomScreens.screens().map { it.route }

    Scaffold(
        topBar = {
            if (stringRoutesStack.contains(currentRoute?.get(0)) && currentRoute?.get(0) != StackScreens.PaymentFinished.name) TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "image back",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            if (stringBottomRoute.contains(currentRoute?.get(0))) BottomCustomNavigation(
                navHostController = navController,
                navDestination = currentDestination
            )
        }) {
        NavGraphApp(navController = navController)
    }


}
