package com.dvt.currencyexchangeapp.utils

sealed class ResponseState {
    object Loading : ResponseState()
    data class Result<T>(val data: T) : ResponseState()
    data class Error(val message: String) : ResponseState()
}