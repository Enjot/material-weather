package com.enjot.materialweather.domain.utils


sealed class Resource<T>(
    val data: T? = null,
    val errorType: ErrorType? = null
) {
    class Success<T>(data: T? = null): Resource<T>(data)
    class Error<T>(errorType: ErrorType = ErrorType.UNKNOWN, data: T? = null): Resource<T>(data, errorType)
}
