package com.example.coffesbarcompose.repository

import android.util.Log
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.GeoCodingModel
import com.example.coffesbarcompose.services.GeoCodingService
import javax.inject.Inject

class GeoCodingRepository @Inject constructor(private val geoCodingService: GeoCodingService) {

    suspend fun getAddress(location: String): DataOrException<GeoCodingModel, Boolean, Exception> {
        val data = try {
            geoCodingService.geoCoding(location)
        } catch (e: Exception) {
            Log.d("Exception Geocode", e.toString())
            return DataOrException(exception = e)
        }
        return DataOrException(data)
    }


}