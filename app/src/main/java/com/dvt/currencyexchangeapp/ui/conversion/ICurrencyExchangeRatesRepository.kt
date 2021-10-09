package com.dvt.currencyexchangeapp.ui.conversion

import com.dvt.network.models.convert.CurrencyConversionResponse
import com.dvt.network.network.ApiResponse

interface ICurrencyExchangeRatesRepository {
    suspend fun getCurrencyExchangeRates(
        apiKey: String,
        from: String,
        to: String,
        amount: String
    ): ApiResponse<CurrencyConversionResponse>
}