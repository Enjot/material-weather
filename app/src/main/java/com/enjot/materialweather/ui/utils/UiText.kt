package com.enjot.materialweather.ui.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.utils.ErrorType

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(
        val id: Int,
        val args: List<Any> = emptyList()
    ): UiText()
    
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args.toTypedArray())
        }
    }
    
    @Composable
    fun asString(): String {
        val context = LocalContext.current.applicationContext
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args.toTypedArray())
        }
    }
}

fun ErrorType.toUiText(): UiText {
   return when (this) {
       ErrorType.SERVER -> UiText.StringResource(R.string.server_not_responding)
       ErrorType.NETWORK -> UiText.StringResource(R.string.no_internet_connection)
       ErrorType.LOCATION -> UiText.StringResource(R.string.no_access_location)
       ErrorType.NO_RESULTS -> UiText.StringResource(R.string.no_places_found)
       ErrorType.UNKNOWN -> UiText.StringResource(R.string.unknown_error)
    }
}