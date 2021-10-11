package com.dvt.currencyexchangeapp.ui.conversion.repository

import com.dvt.network.api.ApiService
import com.dvt.network.network.ApiResponse
import com.dvt.network.network.apiCall

class CurrencyExchangeRatesRepository(
    private val apiService: ApiService
) : ICurrencyExchangeRatesRepository {

    override suspend fun getCurrencyExchangeRates(
        apiKey: String,
        from: String,
        to: String,
        amount: Double
    ): ApiResponse<com.dvt.network.models.convert.ConversionResponse> {
        return apiCall {
            apiService.convert(apiKey, from, to, amount)
        }
    }
}