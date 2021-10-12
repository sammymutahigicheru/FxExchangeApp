package com.dvt.network.api

import com.dvt.network.models.CurrencyResponse
import com.dvt.network.models.convert.ConversionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("apicurrencies")
    suspend fun getCurrencies(
        @Query("api_key") apiKey: String
    ): CurrencyResponse

    @GET("convert")
    suspend fun convert(
        @Query("api_key") apiKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ConversionResponse

    @GET("historical/{date}")
    suspend fun historicalExchangeRates(
        @Path("date") date:String,
        @Query("api_key") apiKey: String
    ):ConversionResponse
}