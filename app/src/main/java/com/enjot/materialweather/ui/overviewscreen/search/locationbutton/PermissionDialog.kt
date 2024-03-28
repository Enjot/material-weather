package com.enjot.materialweather.ui.overviewscreen.search.locationbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (isPermanentlyDeclined) {
                TextButton(onClick = onGoToAppSettingsClick) {
                    Text("Open Settings")
                }
            } else {
                TextButton(onClick = onOkClick) {
                    Text("Grant permission")
                }
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        icon = {
               Icon(
                   imageVector = if (isPermanentlyDeclined) Icons.Outlined.LocationOff else Icons.Outlined.LocationOn,
                   contentDescription = null)
        },
        title = { Text(text = "Location permission required") },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        }
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CoarseLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you didn't allow for approximately location permission. " +
                    "This app needs both approximately and precise location permissions to get your current location. " +
                    "You can grant them manually in settings."
        } else {
            "This app needs access to your approximately location. Otherwise use search option."
        }
    }
}

class FineLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you didn't allow for precise location permission. " +
                    "This app needs both approximately and precise location permissions to get your current location. " +
                    "You can grant them manually in settings."
        } else {
            "This app needs access to your precise location. Otherwise use search option."
        }
    }
}
