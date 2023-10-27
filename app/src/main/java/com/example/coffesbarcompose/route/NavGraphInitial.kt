package com.example.coffesbarcompose.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coffesbarcompose.screen.MainScreen
import com.example.coffesbarcompose.screen.login.LogInScreen
import com.example.coffesbarcompose.screen.sign_in.SignIn

@Composable
fun NavGraphInitial(navController: NavHostController) {

    NavHost(navController = navController, startDestination = StackScreensInitial.Login.name) {
        composable(StackScreensInitial.Login.name) {
            LogInScreen(navController = navController)
        }
        composable(StackScreensInitial.SignIn.name) {
            SignIn(navController = navController)
        }
        composable(StackScreensInitial.MainScreen.name) {
            MainScreen()
        }
    }


}