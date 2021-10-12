package com.dvt.currencyexchangeapp.ui.histories

import com.dvt.network.api.ApiService
import com.dvt.network.models.convert.ConversionResponse
import com.dvt.network.network.ApiResponse
import com.dvt.network.network.apiCall

class HistoriesRepository(
    private val apiService: ApiService
):IHistoriesRepository {
    override suspend fun historicalExchangeRates(
        date: String,
        apiKey: String
    ): ApiResponse<ConversionResponse> {
        return apiCall {
            apiService.historicalExchangeRates(
                date,
                apiKey
            )
        }
    }
}