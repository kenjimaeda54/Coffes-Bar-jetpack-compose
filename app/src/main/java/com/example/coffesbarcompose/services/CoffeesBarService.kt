package com.example.coffesbarcompose.services

import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface CoffeesBarServiceApi {
    @GET("/coffees")
    suspend fun getAllCoffees(): List<CoffeesModel>

    @POST("/users/sigin")
    suspend fun createUser(
        @Body userModel: UserModel
    ): UserModel

}