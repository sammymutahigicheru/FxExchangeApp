package com.dvt.currencyexchangeapp.ui.histories

import com.dvt.network.models.convert.ConversionResponse
import com.dvt.network.network.ApiResponse

interface IHistoriesRepository {

    suspend fun historicalExchangeRates(
        date:String,
        apiKey:String
    ):ApiResponse<ConversionResponse>
}