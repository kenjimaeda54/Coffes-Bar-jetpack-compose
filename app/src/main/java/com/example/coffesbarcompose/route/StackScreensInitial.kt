package com.example.coffesbarcompose.route

import java.lang.IllegalArgumentException

enum class StackScreensInitial {
    Login,
    SignIn,
    MainScreen;

    companion object {
        fun fromRoute(route: String): StackScreensInitial = when (route.substringBefore("/")) {
            Login.name -> Login
            SignIn.name -> SignIn
            MainScreen.name -> MainScreen
            else -> throw IllegalArgumentException("Route $route is not recognizable")
        }
    }
}