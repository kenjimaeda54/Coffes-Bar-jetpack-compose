package com.example.coffesbarcompose.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coffesbarcompose.screen.login.LoginScreen
import com.example.coffesbarcompose.screen.sign_in.SignIn

@Composable
fun NavGraphInitial(navController: NavHostController) {

    NavHost(navController = navController, startDestination = StackScreensInitial.SignIn.name) {
        composable(StackScreensInitial.Login.name) {
            LoginScreen()
        }
        composable(StackScreensInitial.SignIn.name) {
            SignIn()
        }
    }


}