package com.dvt.currencyexchangeapp.ui.conversion.repository

import com.dvt.network.models.convert.ConversionResponse
import com.dvt.network.network.ApiResponse

interface ICurrencyExchangeRatesRepository {

    suspend fun getCurrencyExchangeRates(
        apiKey: String,
        from: String,
        to: String,
        amount: Double
    ): ApiResponse<ConversionResponse>
}