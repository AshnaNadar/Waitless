package org.example.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import android.location.Location
import com.google.android.gms.location.Priority

object LocationUtils {
    private const val RADIUS = 3218.69 // 2 miles in meters

    // This function initiates a location request and provides the result to the callback.
    fun getUserLocation(context: Context, callback: (Location?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Creating a LocationRequest using Builder
        val locationRequest = LocationRequest.Builder(10000L) // Update interval in milliseconds
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdates(1)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                callback(locationResult.lastLocation)
            }
        }

        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocationPermission) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, context.mainLooper)
        } else {
            // Handle case where location permissions are not granted
            callback(null)
        }
    }

    // This function calculates the distance between the current location and target coordinates.
    fun isWithinLocationRange(currentLocation: Location, targetLat: Double, targetLon: Double): Boolean {
        val results = FloatArray(1)
        Location.distanceBetween(
            currentLocation.latitude,
            currentLocation.longitude,
            targetLat,
            targetLon,
            results
        )
        return results[0] <= RADIUS
    }
}
