package pl.audiotocom.enjot.materialweather.core.data.location

import android.location.Location
import com.enjot.materialweather.core.domain.Coordinates

fun Location.toCoordinates(): Coordinates {
    return Coordinates(
        lat = latitude,
        lon = longitude
    )
}
