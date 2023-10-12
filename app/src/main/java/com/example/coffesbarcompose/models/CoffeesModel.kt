package com.example.coffesbarcompose.models

data class CoffeesModel(
    val _id: String,
    val urlPhoto: String,
    val name: String,
    val description: String,
    val quantityMl: List<String>,
    val price: String
)
