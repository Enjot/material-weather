package com.enjot.materialweather.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(context: Context?, onPermissionsGranted:()->Unit) {
    val permissionAlreadyRequested = rememberSaveable {
        mutableStateOf(false)
    }
    
    val permissionState = rememberMultiplePermissionsState(
        permissions =
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) {
        permissionAlreadyRequested.value = true
        if (!it.values.contains(false)){
            onPermissionsGranted.invoke()
        }
    }
    
    if (!permissionAlreadyRequested.value && !permissionState.shouldShowRationale) {
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    } else if (permissionState.shouldShowRationale) {
        ShowRationaleContent {
            permissionState.launchMultiplePermissionRequest()
        }
        
    } else {
        ShowOpenSettingsContent {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri: Uri = Uri.fromParts("package", context?.packageName, null)
            intent.data = uri
            context?.startActivity(intent)
        }
    }
    
}

@Composable
fun ShowRationaleContent(content: () -> Unit) {

}

@Composable
fun ShowOpenSettingsContent(content: () -> Unit) {

}
