package com.enjot.materialweather.ui.overviewscreen.search.locationbutton

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CurrentLocationButtonViewModel : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    
    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }
    
    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}