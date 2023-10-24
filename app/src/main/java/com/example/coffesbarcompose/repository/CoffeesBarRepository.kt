package com.example.coffesbarcompose.repository

import android.util.Log
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.UserModel
import com.example.coffesbarcompose.services.CoffeesBarServiceApi
import javax.inject.Inject

class CoffeesBarRepository @Inject constructor(private val coffeesBarServiceApi: CoffeesBarServiceApi) {

    suspend fun getAllCoffees(): DataOrException<List<CoffeesModel>, Boolean, Exception> {
        val data = try {
            coffeesBarServiceApi.getAllCoffees()
        } catch (exception: Exception) {
            Log.d("Error", exception.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = data)
    }

    suspend fun createUser(userModel: UserModel): DataOrException<UserModel, Boolean, Exception> {
        val data = try {
            coffeesBarServiceApi.createUser(userModel)
        } catch (exception: Exception) {
            Log.d("Error", exception.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = data)
    }

}