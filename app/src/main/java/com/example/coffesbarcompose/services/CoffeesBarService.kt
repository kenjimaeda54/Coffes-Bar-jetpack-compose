package com.example.coffesbarcompose.services

import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.CreateCartModel
import com.example.coffesbarcompose.models.UpdateAvatarModel
import com.example.coffesbarcompose.models.UserLoginModel
import com.example.coffesbarcompose.models.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface CoffeesBarServiceApi {
    @GET("/coffees")
    suspend fun getAllCoffees(): List<CoffeesModel>

    @POST("/users/sigin")
    suspend fun createUser(
        @Body userModel: UserModel
    ): List<UserModel>

    @POST("/users/login")
    suspend fun loginUser(
        @Body userLoginModel: UserLoginModel
    ): UserModel


    @POST("/carts")
    suspend fun createCart(
        @Body createCartModel: CreateCartModel
    )

    @GET("/avatars")
    suspend fun getAvatars(): List<AvatarModel>

    @GET("/avatars/{id}")
    suspend fun getOnlyAvatar(@Path("id") id: String): AvatarModel

    @POST("/users/avatar")
    suspend fun updateAvatarUser(
        @Query("userId") userId: String,
        @Body updateAvatarModel: UpdateAvatarModel
    )


}