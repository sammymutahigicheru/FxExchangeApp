package com.dvt.currencyexchangeapp.ui.currencies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.currencyexchangeapp.ui.currencies.repository.CurrencyRepository
import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.network.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val _currencies: MutableStateFlow<ResponseState> = MutableStateFlow(
        ResponseState.Loading
    )
    val currencies: StateFlow<ResponseState> = _currencies.asStateFlow()

    fun getCurrencies(apiKey: String) = viewModelScope.launch {
        when (
            val response = currencyRepository.getCurrencies(apiKey)
        ) {
            is ApiResponse.Success -> {
                val result = response.data
                _currencies.value = ResponseState.Result(result)
            }
            is ApiResponse.DVTError -> {
                _currencies.value =
                    ResponseState.Error("Something occurred when fetching currencies")
            }
            is ApiResponse.ServerError -> {
                val error = response.errorBody
                _currencies.value =
                    error?.message?.let { ResponseState.Error(it) }!!
            }
        }
    }
}