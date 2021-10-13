package com.dvt.currencyexchangeapp.ui.histories

import com.dvt.network.api.ApiService
import com.dvt.network.models.convert.ConversionResponse
import com.dvt.network.network.ApiResponse
import com.dvt.network.network.apiCall
import com.sammy.data.dao.RatesDao
import com.sammy.data.entity.RatesEntity
import kotlinx.coroutines.flow.Flow

class HistoriesRepository(
    private val apiService: ApiService,
    private val ratesDao: RatesDao
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

    override suspend fun fetchAllCurrencyRates(): Flow<List<RatesEntity>> {
        return ratesDao.historicalRates()
    }

    override fun saveRates(rates: List<RatesEntity>) {
        ratesDao.insert(rates)
    }

}