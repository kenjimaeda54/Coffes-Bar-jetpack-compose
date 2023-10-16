package com.example.coffesbarcompose.repository

import android.util.Log
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.services.CoffeesBarService
import javax.inject.Inject

class CoffeesBarRepository @Inject constructor(private val coffeesBarService: CoffeesBarService) {

    suspend fun getAllCoffees(): DataOrException<List<CoffeesModel>,Boolean,Exception> {
        val data = try {
            coffeesBarService.getAllCoffees()
        }catch (exception: Exception) {
            Log.d("Error", exception.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = data)
    }

}