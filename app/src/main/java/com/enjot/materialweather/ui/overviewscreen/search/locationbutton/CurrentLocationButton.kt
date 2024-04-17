package com.enjot.materialweather.ui.overviewscreen.search.locationbutton

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.enjot.materialweather.R

@Composable
fun CurrentLocationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val visiblePermissionDialogQueue = remember {
        mutableStateListOf<String>()
    }
    
    val context = LocalContext.current
    
    val permissionsToRequest = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
    
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                val isGranted = perms[permission] == true
                if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
                    visiblePermissionDialogQueue.add(permission)
                }
            }
            if (isCoarseLocationGranted(context) && isFineLocationGranted(context)) {
                onClick()
            }
        }
    )
    
    visiblePermissionDialogQueue
        .reversed()
        .forEach { permission ->
            
            if (permission == ACCESS_COARSE_LOCATION && isCoarseLocationGranted(context))
                visiblePermissionDialogQueue.removeFirst()
            
            if (permission == ACCESS_FINE_LOCATION && isFineLocationGranted(context))
                visiblePermissionDialogQueue.removeFirst()
            
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    ACCESS_COARSE_LOCATION -> CoarseLocationPermissionTextProvider()
                    ACCESS_FINE_LOCATION -> FineLocationPermissionTextProvider()
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(context as Activity, permission),
                onDismiss = { visiblePermissionDialogQueue.clear() },
                onOkClick = {
                    visiblePermissionDialogQueue.removeFirst()
                    multiplePermissionResultLauncher.launch(arrayOf(permission))
                },
                onGoToAppSettingsClick = {
                    visiblePermissionDialogQueue.clear()
                    context.openAppSettings()
                }
            )
        }
    
    Button(
        onClick = {
            if (isCoarseLocationGranted(context) && isFineLocationGranted(context)) {
                onClick()
            } else {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
            }
        },
        modifier = modifier
    ) {
        val style = MaterialTheme.typography.titleMedium
        val iconScaleFactor = 1.1f
        Text(
            text = stringResource(R.string.use_current_location),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Outlined.MyLocation,
            contentDescription = null,
            modifier = Modifier.size(style.fontSize.value.dp * iconScaleFactor)
        )
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

private fun isCoarseLocationGranted(context: Context) =
    ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


private fun isFineLocationGranted(context: Context) =
    ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED


@Preview
@Composable
fun UseCurrentLocationButtonPreview() {
    
    CurrentLocationButton(
        onClick = { }
    )
}