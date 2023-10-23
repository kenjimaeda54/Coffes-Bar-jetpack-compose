package com.example.coffesbarcompose.route

enum class StackScreensApp {
    DetailsScreen,
    PaymentResume,
    PaymentFinished;

    companion object {
        fun fromRoute(route: String?): StackScreensApp = when(route?.substringBefore("/")) {
            DetailsScreen.name -> DetailsScreen
            PaymentResume.name-> PaymentResume
            PaymentFinished.name -> PaymentFinished
            else -> throw  IllegalArgumentException("Route $route is not recognizable")
        }
    }

}