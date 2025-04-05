package com.enjot.materialweather.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}