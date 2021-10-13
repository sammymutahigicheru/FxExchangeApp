package com.dvt.currencyexchangeapp.models

import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.models.convert.ConversionResponse
import com.dvt.network.models.convert.Rates

private var hashMap = HashMap<String, Rates>()
fun getHashMap(): HashMap<String, Rates> {
    hashMap["GBP"] = Rates(
        "Pound sterling",
        "rate",
        8.4888
    )
    return hashMap
}

val exchangeRatesResponse = ConversionResponse(
    "10.0000",
    "EUR",
    "Euro",
    getHashMap(),
    "success",
    "2021-10-13"
)

val exchangeRatesResult = ResponseState.Result(exchangeRatesResponse)