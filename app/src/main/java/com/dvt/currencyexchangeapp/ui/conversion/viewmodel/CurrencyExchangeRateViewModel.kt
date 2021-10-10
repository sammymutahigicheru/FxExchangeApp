package com.dvt.currencyexchangeapp.ui.conversion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.currencyexchangeapp.ui.conversion.repository.ICurrencyExchangeRatesRepository
import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.network.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CurrencyExchangeRateViewModel(
    val exchangeRatesRepo: ICurrencyExchangeRatesRepository
):ViewModel() {
    private val _exchangeRates:MutableStateFlow<ResponseState> = MutableStateFlow(
        ResponseState.Loading
    )
    val exchangeRates:StateFlow<ResponseState> = _exchangeRates.asStateFlow()

    fun getExchangeRates(
        apiKey:String,
        from:String,
        to:String,
        amount:Double
    )  = viewModelScope.launch {
        when(
            val response = exchangeRatesRepo.getCurrencyExchangeRates(
                apiKey,
                from,
                to,
                amount
            )
        ){
            is ApiResponse.Success ->{
               _exchangeRates.value = ResponseState.Result(response.data)
            }
            is ApiResponse.ServerError ->{
                _exchangeRates.value = ResponseState.Error(response.errorBody?.message.toString())
            }
            is ApiResponse.DVTError ->{
                _exchangeRates.value = ResponseState.Error("Something went wrong while fetching exchange rates, try again later")
            }
        }
    }
}