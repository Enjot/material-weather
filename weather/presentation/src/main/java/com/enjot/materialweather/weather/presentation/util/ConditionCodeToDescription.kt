package com.enjot.materialweather.weather.presentation.util

import com.enjot.materialweather.weather.presentation.R

fun conditionCodeToDescriptionStringRes(code: String): Int {
    return when(code) {
        
        // Group 2xx: Thunderstorm
        "200" -> R.string.thunderstorm_with_light_rain
        "201" -> R.string.thunderstorm_with_rain
        "202" -> R.string.thunderstorm_with_heavy_rain
        "210" -> R.string.light_thunderstorm
        "211" -> R.string.thunderstorm
        "212" -> R.string.heavy_thunderstorm
        "221" -> R.string.ragged_thunderstorm
        "230" -> R.string.thunderstorm_with_light_drizzle
        "231" -> R.string.thunderstorm_with_drizzle
        "232" -> R.string.thunderstorm_with_heavy_drizzle
        
        // Group 3xx: Drizzle
        "300" -> R.string.light_intensity_drizzle
        "301" -> R.string.drizzle
        "302" -> R.string.heavy_intensity_drizzle
        "310" -> R.string.light_intensity_drizzle_rain
        "311" -> R.string.drizzle_rain
        "312" -> R.string.heavy_intensity_drizzle_rain
        "313" -> R.string.shower_rain_and_drizzle
        "314" -> R.string.heavy_shower_rain_and_drizzle
        "321" -> R.string.shower_drizzle
        
        // Group 5xx: Rain
        "500" -> R.string.light_rain
        "501" -> R.string.moderate_rain
        "502" -> R.string.heavy_intensity_rain
        "503" -> R.string.very_heavy_rain
        "504" -> R.string.extreme_rain
        "511" -> R.string.freezing_rain
        "520" -> R.string.light_intensity_shower_rain
        "521" -> R.string.shower_rain
        "522" -> R.string.heavy_intensity_shower_rain
        "531" -> R.string.ragged_shower_rain
        
        // Group 6xx: Snow
        "600" -> R.string.light_snow
        "601" -> R.string.snow
        "602" -> R.string.heavy_snow
        "611" -> R.string.sleet
        "612" -> R.string.light_shower_sleet
        "613" -> R.string.shower_sleet
        "615" -> R.string.light_rain_and_snow
        "616" -> R.string.rain_and_snow
        "620" -> R.string.light_shower_snow
        "621" -> R.string.shower_snow
        "622" -> R.string.heavy_shower_snow
        
        // Group 7xx: Atmosphere
        "701" -> R.string.mist
        "711" -> R.string.smoke
        "721" -> R.string.haze
        "731" -> R.string.sand_dust_whirls
        "741" -> R.string.fog
        "751" -> R.string.sand
        "761" -> R.string.dust
        "762" -> R.string.volcanic_ash
        "771" -> R.string.squalls
        "781" -> R.string.tornado
        
        // Group 800: Clear
        "800" -> R.string.clear_sky
        
        // Group 80x: Clouds
        "801" -> R.string.few_clouds
        "802" -> R.string.scattered_clouds
        "803" -> R.string.broken_clouds
        "804" -> R.string.overcast_clouds
        
        else -> R.string.no_description
    }
}