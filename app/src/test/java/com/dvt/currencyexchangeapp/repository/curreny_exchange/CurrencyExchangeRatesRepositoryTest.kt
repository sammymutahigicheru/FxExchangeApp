package com.dvt.currencyexchangeapp.repository.curreny_exchange

import com.dvt.network.api.ApiService
import com.dvt.network.helpers.Constants
import com.dvt.network.helpers.CurrencyExchangeRequestDispatcher
import com.dvt.network.models.convert.CurrencyConversionResponse
import com.dvt.network.network.ApiResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class CurrencyExchangeRatesRepositoryTest : Spek({
    lateinit var mockWebServer: MockWebServer

    lateinit var apiService: ApiService

    lateinit var okHttpClient: OkHttpClient

    lateinit var loggingInterceptor: HttpLoggingInterceptor

    lateinit var currencyExchangeRatesRepository: ICurrencyExchangeRatesRepository

    lateinit var result: ApiResponse<CurrencyConversionResponse>

    Feature("Fetching Weather Reports from API") {
        beforeEachScenario {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = CurrencyExchangeRequestDispatcher()
            mockWebServer.start()
            loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            apiService = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ApiService::class.java)
            currencyExchangeRatesRepository = CurrencyExchangeRatesRepository(apiService)
        }

        afterEachScenario {
            mockWebServer.shutdown()
        }

        Scenario("Fetching the currency conversion rates") {
            Given("the right api key return currency exchange rates") {
                runBlocking {
                    result = currencyExchangeRatesRepository.getCurrencyExchangeRates(
                        Constants.API_KEY,
                        Constants.FROM,
                        Constants.TO,
                        Constants.AMOUNT
                    )
                }
            }
            Then("Given right query params get correct conversion rates") {
                when (result) {
                    is ApiResponse.Success -> {
                        val data: CurrencyConversionResponse =
                            (result as ApiResponse.Success<CurrencyConversionResponse>).data
                        Truth.assertThat(data.price).isEqualTo(10.11725)
                    }
                    else -> {
                    }
                }
            }
        }
    }
})