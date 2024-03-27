package com.enjot.materialweather.ui.overviewscreen.search.locationbutton

import android.Manifest
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CurrentLocationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CurrentLocationButtonViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    
    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )
    
    dialogQueue
        .reversed()
        .forEach { permission ->
            if (permission == Manifest.permission.ACCESS_COARSE_LOCATION &&
                isCoarseLocationAlreadyGranted(context)
            ) viewModel.dismissDialog()
            if (permission == Manifest.permission.ACCESS_FINE_LOCATION &&
                isFineLocationAlreadyGranted(context)
            ) viewModel.dismissDialog()
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        CoarseLocationPermissionTextProvider()
                    }
                    
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        FineLocationPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    context as Activity, permission
                ),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {
                    viewModel.dismissDialog()
                    context.openAppSettings()
                }
            )
        }
    
    val style = MaterialTheme.typography.titleMedium
    val iconScaleFactor = 1.2f
    
    Button(
        onClick = {
            if (isCoarseLocationAlreadyGranted(context) && isFineLocationAlreadyGranted(context)) {
                onClick()
            } else {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
            }
        },
        modifier = modifier
    ) {
        Text(
            text = "Use current location",
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

private fun isCoarseLocationAlreadyGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

private fun isFineLocationAlreadyGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

@Preview
@Composable
fun UseCurrentLocationButtonPreview() {
    
    CurrentLocationButton(
        onClick = { }
    )
}