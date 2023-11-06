package com.example.coffesbarcompose.repository

import android.util.Log
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.CreateCartModel
import com.example.coffesbarcompose.models.OrdersByUserModel
import com.example.coffesbarcompose.models.UpdateAvatarModel
import com.example.coffesbarcompose.models.UserLoginModel
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
            return DataOrException(exception = exception, isLoading = false)
        }
        return DataOrException(data = data[0], isLoading = false)
    }

    suspend fun loginUser(userLoginModel: UserLoginModel): DataOrException<UserModel, Boolean, Exception> {
        val data = try {
            coffeesBarServiceApi.loginUser(userLoginModel)
        } catch (exception: Exception) {
            Log.d("Error", exception.toString())
            return DataOrException(exception = exception, isLoading = false)
        }
        return DataOrException(data = data, isLoading = false)
    }

    suspend fun updateAvatarUser(
        userId: String,
        updateAvatarModel: UpdateAvatarModel
    ): DataOrException<Boolean, Boolean, Exception> {
        return try {
            coffeesBarServiceApi.updateAvatarUser(userId, updateAvatarModel)
            DataOrException(data = true, isLoading = false)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            DataOrException(exception = e, isLoading = false)
        }

    }

    suspend fun getAvatars(): DataOrException<List<AvatarModel>, Boolean, Exception> {
        val data = try {
            coffeesBarServiceApi.getAvatars()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            return DataOrException(exception = e)
        }
        return DataOrException(data = data)
    }

    suspend fun getOnlyAvatar(id: String): DataOrException<AvatarModel, Boolean, Exception> {
        val data = try {
            coffeesBarServiceApi.getOnlyAvatar(id)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            return DataOrException(exception = e)
        }
        return DataOrException(data = data)
    }

    suspend fun createCart(createCartModel: CreateCartModel): DataOrException<Boolean, Boolean, Exception> {
        try {
            coffeesBarServiceApi.createCart(createCartModel)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            return DataOrException(exception = e)
        }
        return DataOrException(data = true)
    }


    suspend fun getOrders(userId: String): DataOrException<List<OrdersByUserModel>, Boolean, Exception> {
        val data = try {
            coffeesBarServiceApi.getOrders(userId)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            return DataOrException(exception = e)
        }
        return DataOrException(data = data)
    }


}