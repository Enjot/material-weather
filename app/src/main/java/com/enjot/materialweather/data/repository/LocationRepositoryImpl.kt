package com.enjot.materialweather.data.repository

import android.annotation.SuppressLint
import android.location.Location
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class LocationRepositoryImpl @Inject constructor(
    private val client: FusedLocationProviderClient,
) : LocationRepository {
    
    @SuppressLint("MissingPermission")
    override suspend fun getCoordinates(): Resource<Coordinates?> {
        return try {
            suspendCancellableCoroutine { continuation ->
                client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location: Location? ->
                        val coordinates = location?.let {
                            Coordinates(
                                it.latitude,
                                it.longitude
                            )
                        }
                        continuation.resume(Resource.Success(coordinates))
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
        } catch (e: Exception) {
            return Resource.Error("Unable to access location")
        }
    }
}