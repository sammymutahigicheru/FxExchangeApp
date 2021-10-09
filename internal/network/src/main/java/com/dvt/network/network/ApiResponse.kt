package com.dvt.network.network

sealed class ApiResponse<out R> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class ServerError(
        val code: Int? = null,
        val errorBody: ErrorResponse? = null
    ) : ApiResponse<Nothing>()

    object DVTError : ApiResponse<Nothing>()
}