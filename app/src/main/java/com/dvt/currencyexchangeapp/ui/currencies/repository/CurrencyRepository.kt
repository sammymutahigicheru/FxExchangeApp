package com.dvt.currencyexchangeapp.ui.currencies.repository

import com.dvt.network.models.CurrencyResponse
import com.dvt.network.network.ApiResponse

interface CurrencyRepository {
    suspend fun getCurrencies(apiKey:String):ApiResponse<CurrencyResponse>
}