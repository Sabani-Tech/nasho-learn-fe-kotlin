package com.learn.nasho.data

sealed class ResultState<out T: Any?> {

    data object Loading : ResultState<Nothing>()
    data class Success<out T: Any>(val data: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
}