package com.dvt.currencyexchangeapp.ui.histories

import com.dvt.network.models.convert.ConversionResponse
import com.dvt.network.network.ApiResponse
import com.sammy.data.entity.RatesEntity
import kotlinx.coroutines.flow.Flow

interface IHistoriesRepository {

    suspend fun historicalExchangeRates(
        date:String,
        apiKey:String
    ):ApiResponse<ConversionResponse>

    suspend fun fetchAllCurrencyRates():Flow<List<RatesEntity>>

    suspend fun saveRates(rates: List<RatesEntity>)
}