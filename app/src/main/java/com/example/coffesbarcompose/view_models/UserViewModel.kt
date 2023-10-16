package com.example.coffesbarcompose.view_models

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

data class LocationDetails(val latitude: Double, val longitude: Double)

class UserViewModel : ViewModel() {
    private var locationCallback: LocationCallback? = null
    var currentLocation = mutableStateOf(LocationDetails(latitude = 0.0, longitude = 0.0))

    fun getLocation(activity: Activity) {
         LocationServices.getFusedLocationProviderClient(activity)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val firstLocation = p0.locations.first()
                currentLocation.value =
                    LocationDetails(firstLocation.latitude, firstLocation.longitude)

            }
        }
    }

    //garantir localizao no activy
//    val launcherMultiplePermissions = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissionsMap ->
//        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
//        if (areGranted) {
//            locationRequired = true
//            startLocationUpdates()
//            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }

}