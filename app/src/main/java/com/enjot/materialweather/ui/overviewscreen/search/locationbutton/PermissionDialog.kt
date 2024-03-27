package com.enjot.materialweather.ui.overviewscreen.search.locationbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WrongLocation
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
                    Text("Go to the settings app")
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
               Icon(imageVector = Icons.Default.WrongLocation, contentDescription = null)
        },
        title = { Text(text = "Permission required") },
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
            "It seems you permanently declined approximately location permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your approximately location."
        }
    }
}

class FineLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined precise location permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your precise location."
        }
    }
}
