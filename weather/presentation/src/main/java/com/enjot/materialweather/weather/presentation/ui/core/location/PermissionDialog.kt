package com.enjot.materialweather.weather.presentation.ui.core.location

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.enjot.materialweather.weather.presentation.R


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
                    Text(stringResource(R.string.settings))
                }
            } else {
                TextButton(onClick = onOkClick) {
                    Text(stringResource(R.string.grant_permission))
                }
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dismiss))
            }
        },
        icon = {
               Icon(
                   imageVector = if (isPermanentlyDeclined) Icons.Outlined.LocationOff else Icons.Outlined.LocationOn,
                   contentDescription = null)
        },
        title = { Text(text = stringResource(R.string.location_permission_required)) },
        text = {
            Text(
                text = stringResource(permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                ))
            )
        }
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): Int
}

class CoarseLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): Int {
        return if (isPermanentlyDeclined) {
            R.string.location_permission_denied
        } else {
            R.string.coarse_location_permission_rationale
        }
    }
}

class FineLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): Int {
        return if (isPermanentlyDeclined) {
            R.string.location_permission_denied
        } else {
            R.string.fine_location_permission_rationale
        }
    }
}
