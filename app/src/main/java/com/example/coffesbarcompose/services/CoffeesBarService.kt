package com.example.coffesbarcompose.services

import com.example.coffesbarcompose.models.CoffeesModel
import retrofit2.http.GET


interface CoffeesBarService {
    @GET("/coffees")
    suspend fun getAllCoffees(): List<CoffeesModel>


}