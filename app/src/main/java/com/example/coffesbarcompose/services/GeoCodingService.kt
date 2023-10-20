package com.example.coffesbarcompose.services

import com.example.coffesbarcompose.models.GeoCodingModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GeoCodingService {
    @Headers(
        "X-RapidAPI-Key: cc844489edmshb597fa8b2a59ddcp17f137jsn5e7baae39f33",
        "X-RapidAPI-Host: trueway-geocoding.p.rapidapi.com"
    )
    @GET("/ReverseGeocode")

    suspend fun geoCoding(
        @Query("location") location: String,
        @Query("language") language: String = "pt-Br"
    ): GeoCodingModel
}