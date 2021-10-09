package com.dvt.currencyexchangeapp.ui.currencies.repository

import com.dvt.network.api.ApiService
import com.dvt.network.models.CurrencyResponse
import com.dvt.network.network.ApiResponse
import com.dvt.network.network.apiCall

class CurrencyRespositoryImpl(
    private val apiService: ApiService
) : CurrencyRepository {
    override suspend fun getCurrencies(apiKey: String): ApiResponse<CurrencyResponse> {

        return apiCall {
            apiService.getCurrencies(apiKey)
        }
    }
}