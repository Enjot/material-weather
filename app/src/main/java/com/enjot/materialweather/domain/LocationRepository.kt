package com.enjot.materialweather.domain

import com.google.android.gms.tasks.Task

interface LocationRepository {
    
    fun getCurrentLocation(name: String = "")
}