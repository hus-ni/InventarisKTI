package com.muhammadhusniabdillah.inventariskti.home.scanner

sealed class Resources<T> {
    data class Loading<T>(val data: T?) : Resources<T>()

    data class Error<T>(val error: Exception, val data: T?) : Resources<T>()

    data class Success<T>(val data: T) : Resources<T>()
}
