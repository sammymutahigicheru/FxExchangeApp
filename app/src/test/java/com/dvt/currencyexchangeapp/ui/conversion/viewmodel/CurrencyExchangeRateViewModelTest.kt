package com.dvt.currencyexchangeapp.ui.conversion.viewmodel

import app.cash.turbine.test
import com.dvt.currencyexchangeapp.models.exchangeRatesResponse
import com.dvt.currencyexchangeapp.models.exchangeRatesResult
import com.dvt.currencyexchangeapp.ui.conversion.repository.ICurrencyExchangeRatesRepository
import com.dvt.network.helpers.Constants
import com.dvt.network.network.ApiResponse
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CurrencyExchangeRateViewModelTest:Spek({
    lateinit var currencyExchangeRateViewModel:CurrencyExchangeRateViewModel

    val dispatcher = TestCoroutineDispatcher()

    val currencyExchangeRateRepository = mockk<ICurrencyExchangeRatesRepository>()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching Currency Exchange Rates") {

        beforeEachTest {
            currencyExchangeRateViewModel =
                CurrencyExchangeRateViewModel(currencyExchangeRateRepository)
        }

        test("Assert that an event was received and return it") {

            runBlocking {
                coEvery { currencyExchangeRateRepository.getCurrencyExchangeRates(Constants.API_KEY,Constants.FROM,Constants.TO,Constants.AMOUNT) } returns ApiResponse.Success(
                    data = exchangeRatesResponse
                )
                currencyExchangeRateViewModel.getExchangeRates(Constants.API_KEY,Constants.FROM,Constants.TO,Constants.AMOUNT)
                coVerify { currencyExchangeRateRepository.getCurrencyExchangeRates(Constants.API_KEY,Constants.FROM,Constants.TO,Constants.AMOUNT) }
                currencyExchangeRateViewModel.exchangeRates.test {
                    awaitEvent()
                }
            }
        }

        test("Test that currency exchange rates are fetched successfully") {

            runBlocking {
                coEvery { currencyExchangeRateRepository.getCurrencyExchangeRates(Constants.API_KEY,Constants.FROM,Constants.TO,Constants.AMOUNT) } returns ApiResponse.Success(
                    data = exchangeRatesResponse
                )
                currencyExchangeRateViewModel.getExchangeRates(Constants.API_KEY,Constants.FROM,Constants.TO,Constants.AMOUNT)
                coVerify { currencyExchangeRateRepository.getCurrencyExchangeRates(Constants.API_KEY,Constants.FROM,Constants.TO,Constants.AMOUNT) }
                currencyExchangeRateViewModel.exchangeRates.test {
                    Truth.assertThat(awaitItem()).isEqualTo(exchangeRatesResult)
                }
            }
        }
    }

    afterGroup {
        Dispatchers.resetMain()
    }
})