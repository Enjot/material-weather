package com.enjot.materialweather.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.enjot.materialweather.domain.location.LocationTracker
import com.enjot.materialweather.domain.model.Coordinates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class DefaultLocationTracker @Inject constructor(
    private val application: Application,
    private val client: FusedLocationProviderClient,
) : LocationTracker {
    
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Coordinates? {
        return if (arePermissionsGranted()) {
            try {
                suspendCancellableCoroutine { continuation ->
                    client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener { location: Location? ->
                            val coordinates = location?.let {
                                Coordinates(
                                    it.latitude,
                                    it.longitude
                                )
                            }
                            continuation.resume(coordinates)
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
            } catch (e: Exception) {
                null
            }
        } else {
            
            return null
        }
    }
    
    override fun arePermissionsGranted(): Boolean {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission =
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        
        return hasAccessFineLocationPermission
                || hasAccessCoarseLocationPermission
                || isGpsEnabled
    }
    
}
