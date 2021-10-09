package com.dvt.currencyexchangeapp.ui.currencies.viewmodel

import app.cash.turbine.test
import com.dvt.currencyexchangeapp.models.currencyResponseResult
import com.dvt.currencyexchangeapp.models.testCurrencyResponse
import com.dvt.currencyexchangeapp.ui.currencies.repository.CurrencyRepository
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
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CurrencyViewModelTest:Spek({
    lateinit var currencyViewModel: CurrencyViewModel

    val dispatcher = TestCoroutineDispatcher()

    val currencyRepository = mockk<CurrencyRepository>()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching Currencies") {

        beforeEachTest {
            currencyViewModel =
                CurrencyViewModel(currencyRepository)
        }

        test("Assert that an event was received and return it") {

            runBlocking {
                coEvery { currencyRepository.getCurrencies(Constants.API_KEY) } returns ApiResponse.Success(
                    data = testCurrencyResponse
                )
                currencyViewModel.getCurrencies(Constants.API_KEY)
                coVerify { currencyRepository.getCurrencies(Constants.API_KEY) }
                currencyViewModel.currencies.test {
                    awaitEvent()
                }
            }
        }

        test("Test that currencies are fetched successfully") {

            runBlocking {
                coEvery { currencyRepository.getCurrencies(Constants.API_KEY) } returns ApiResponse.Success(
                    data = testCurrencyResponse
                )
                currencyViewModel.getCurrencies(Constants.API_KEY)
                coVerify { currencyRepository.getCurrencies(Constants.API_KEY) }
                currencyViewModel.currencies.test {
                    Truth.assertThat(awaitItem()).isEqualTo(currencyResponseResult)
                }
            }
        }
    }

    afterGroup {
        Dispatchers.resetMain()
    }
})