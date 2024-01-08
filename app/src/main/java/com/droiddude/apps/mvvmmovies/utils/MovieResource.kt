package com.droiddude.apps.mvvmmovies.utils

sealed class MovieResource<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Success<T>(data : T?) : MovieResource<T>(data)
    class Error<T>(message : String, data : T? = null) : MovieResource<T>(data, message)
    class Loading<T>(val isLoading : Boolean = true) : MovieResource<T>(null)
}
