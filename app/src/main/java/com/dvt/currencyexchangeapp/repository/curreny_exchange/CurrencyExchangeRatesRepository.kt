package com.dvt.currencyexchangeapp.repository.curreny_exchange

import com.dvt.network.api.ApiService
import com.dvt.network.models.convert.CurrencyConversionResponse
import com.dvt.network.network.ApiResponse
import com.dvt.network.network.apiCall

class CurrencyExchangeRatesRepository(
    private val apiService: ApiService
) : ICurrencyExchangeRatesRepository {

    override suspend fun getCurrencyExchangeRates(
        apiKey: String,
        from: String,
        to: String,
        amount: String
    ): ApiResponse<CurrencyConversionResponse> {
        return apiCall {
            apiService.convert(apiKey, from, to, amount)
        }
    }
}