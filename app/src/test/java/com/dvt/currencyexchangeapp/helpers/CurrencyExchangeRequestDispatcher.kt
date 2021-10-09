package com.dvt.network.helpers

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

/**
 * Handles Requests from mock web server
 */
internal class CurrencyExchangeRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/apicurrencies?api_key=F52x7mlsROfTBvJfBK6Z" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(com.dvt.currencyexchangeapp.helpers.getJson("json/currency.json"))
            }
            "/apiconvert?${Constants.CURRENCY_EXCHANGE_PARAMS}" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(
                        com.dvt.currencyexchangeapp.helpers.getJson("json/exchange_rates.json")
                    )

            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
        }
    }

}