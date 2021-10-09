package com.dvt.network.api

import com.dvt.network.models.CurrencyResponse
import com.dvt.network.models.convert.CurrencyConversionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("apicurrencies")
    suspend fun getCurrencies(
        @Query("api_key") apiKey: String
    ): CurrencyResponse

    @GET("apiconvert")
    suspend fun convert(
        @Query("api_key") apiKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String
    ): CurrencyConversionResponse

    @GET("apitimeseries")
    suspend fun historicalExchangeRates(
        @Query("api_key") apiKey: String,
        @Query("currency") from: String,
        @Query("start_date") to: String,
        @Query("end_date") amount: String,
        @Query("format") format: String = "ohlc"
    )
}