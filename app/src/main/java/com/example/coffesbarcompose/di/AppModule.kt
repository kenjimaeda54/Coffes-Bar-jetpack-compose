package com.example.coffesbarcompose.di

import com.example.coffesbarcompose.services.CoffeesBarService
import com.example.coffesbarcompose.services.GeoCodingService
import com.example.coffesbarcompose.utility.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun coffeesBarService(): CoffeesBarService = Retrofit.Builder().baseUrl(Constants.baseURL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(CoffeesBarService::class.java)


    @Provides
    @Singleton
    fun coffessBarGeoCodingService(): GeoCodingService =
        Retrofit.Builder().baseUrl("https://trueway-geocoding.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(GeoCodingService::class.java)
}