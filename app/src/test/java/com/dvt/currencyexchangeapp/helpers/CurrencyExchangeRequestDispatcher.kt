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
            "/convert?api_key=9f6394ec58802eb6f2f6195737ba5d1129d97b55" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(com.dvt.currencyexchangeapp.helpers.getJson("json/currency.json"))
            }
            "/historical?${Constants.CURRENCY_EXCHANGE_PARAMS}" -> {
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