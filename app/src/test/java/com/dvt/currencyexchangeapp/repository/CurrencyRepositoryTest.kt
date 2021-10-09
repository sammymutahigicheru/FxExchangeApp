package com.dvt.currencyexchangeapp.repository

import com.dvt.currencyexchangeapp.ui.currencies.repository.CurrencyRepository
import com.dvt.currencyexchangeapp.ui.currencies.repository.CurrencyRespositoryImpl
import com.dvt.network.api.ApiService
import com.dvt.network.helpers.Constants
import com.dvt.network.helpers.CurrencyExchangeRequestDispatcher
import com.dvt.network.models.CurrencyResponse
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

class CurrencyRepositoryTest:Spek({

    lateinit var mockWebServer: MockWebServer

    lateinit var apiService: ApiService

    lateinit var okHttpClient: OkHttpClient

     lateinit var loggingInterceptor: HttpLoggingInterceptor

    lateinit var currencyRepository: CurrencyRepository

    lateinit var result: ApiResponse<CurrencyResponse>

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
            currencyRepository = CurrencyRespositoryImpl(apiService)
        }

        afterEachScenario {
            mockWebServer.shutdown()
        }

        Scenario("Fetching the currency") {
            Given("the right api key return all currencies"){
                runBlocking {
                    result = currencyRepository.getCurrencies(Constants.API_KEY)
                }
            }
            Then("Given Correct Api Key return All Currencies"){
                when(result){
                    is ApiResponse.Success ->{
                        val data:CurrencyResponse = (result as ApiResponse.Success<CurrencyResponse>).data
                        Truth.assertThat(data.currencies.bTCUSD).isEqualTo("Bitcoin")
                    }
                    else -> {}
                }
            }
        }
    }

})