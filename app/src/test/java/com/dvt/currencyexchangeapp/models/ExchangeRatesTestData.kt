package com.dvt.currencyexchangeapp.models

import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.models.convert.CurrencyConversionResponse

val exchangeRatesResponse = CurrencyConversionResponse(
    "EUR",
    10.11725,
    1633767842,
    "SEK",
    10117.25
)

val exchangeRatesResult = ResponseState.Result(exchangeRatesResponse)