package com.dvt.currencyexchangeapp.ui.histories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.network.ApiResponse
import com.sammy.data.entity.RatesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historiesRepository: IHistoriesRepository
) : ViewModel() {
    private val _historicalExchangeRates: MutableStateFlow<ResponseState> = MutableStateFlow(
        ResponseState.Loading
    )
    val historicalExchangeRates: StateFlow<ResponseState> = _historicalExchangeRates.asStateFlow()

    fun getHistoricalExchangeRates(
        date: String,
        apiKey: String
    ) = viewModelScope.launch {
        when (
            val response = historiesRepository.historicalExchangeRates(
                date,
                apiKey
            )
        ) {
            is ApiResponse.Success -> {
                _historicalExchangeRates.value = ResponseState.Result(response.data)
            }
            is ApiResponse.ServerError -> {
                _historicalExchangeRates.value =
                    ResponseState.Error(response.errorBody?.message.toString())
            }
            is ApiResponse.DVTError -> {
                _historicalExchangeRates.value =
                    ResponseState.Error("Something went wrong while fetching exchange rates, try again later")
            }
        }
    }

    fun saveRates(rates: List<RatesEntity>) = viewModelScope.launch(Dispatchers.IO) {
        historiesRepository.saveRates(rates)
    }

    suspend fun fetchHistoricalRates() = historiesRepository.fetchAllCurrencyRates()
}