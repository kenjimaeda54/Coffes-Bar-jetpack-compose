package com.example.coffesbarcompose.models

data class GeoCodingModel(
    val results: List<Result>
)

data class Result(
    val address: String,
    val area: String,
    val country: String,
    val house: String,
    val locality: String,
    val location: Location,
    val location_type: String,
    val neighborhood: String,
    val postal_code: String,
    val region: String,
    val street: String,
    val type: String
)


data class Location(
    val lat: Double,
    val lng: Double
)