package pl.audiotocom.enjot.materialweather.core.data.location

import android.annotation.SuppressLint
import android.location.Location
import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.core.domain.LocationRepository
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.core.domain.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl(
    private val client: FusedLocationProviderClient,
) : LocationRepository {

    @SuppressLint("MissingPermission") // UI checks it TODO check it anyway
    override suspend fun getCoordinates(): Resource<Coordinates?> {
        return try {
            suspendCancellableCoroutine { continuation ->
                client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location: Location? ->
                        val coordinates =
                            location?.toCoordinates() // possible location as null but not sure yet
                        continuation.resume(Resource.Success(coordinates))
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
        } catch (_: Exception) {
            return Resource.Error(ErrorType.LOCATION)
        }
    }
}