package com.example.coffesbarcompose.di


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.coffesbarcompose.models.UserCacheViewModel
import com.example.coffesbarcompose.services.CoffeesBarServiceApi
import com.example.coffesbarcompose.services.GeoCodingService
import com.example.coffesbarcompose.utility.Constants
import com.example.coffesbarcompose.utility.UserCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun coffeesBarService(): CoffeesBarServiceApi = Retrofit.Builder().baseUrl(Constants.baseURL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(CoffeesBarServiceApi::class.java)


    @Provides
    @Singleton
    fun coffessBarGeoCodingService(): GeoCodingService =
        Retrofit.Builder().baseUrl("https://trueway-geocoding.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(GeoCodingService::class.java)


    @Provides
    @Singleton
    fun coffeessProvideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("session_prefs", MODE_PRIVATE)

    @Provides
    @Singleton
    fun coffeesUserCache(sharedPreferences: SharedPreferences): UserCacheViewModel =
        UserCacheViewModel(sharedPreferences)

}