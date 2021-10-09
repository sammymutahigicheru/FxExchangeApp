package com.dvt.currencyexchangeapp.repository.curreny_exchange

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