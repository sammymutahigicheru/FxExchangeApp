package com.dvt.currencyexchangeapp.models

import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.models.Currencies
import com.dvt.network.models.CurrencyResponse
import com.dvt.network.network.ApiResponse

val testCurrencyResponse = CurrencyResponse(
    currencies = Currencies(
        "United Arab Emirates Dirham",
        "Argentine Peso",
        "Australian Dollar",
        "Brazilian Real",
        "Bitcoin",
        "Canadian Dollar",
        "Swiss Franc",
        "Chilean Peso",
        "Chinese Yuan",
        "Colombian Peso",
        "Czech Koruna",
        "Danish Krone",
        "Euro",
        "British Pound Sterling",
        "Hong Kong Dollar",
        "Hungarian Forint",
        "Croatian Kuna",
        "Indonesian Rupiah",
        "Israeli Sheqel",
        "Indian Rupee",
        "Icelandic Krona",
        "Japanese Yen",
        "South Korean Won",
        "Kuwaiti Dinar",
        "Mexican Peso",
        "Malaysian Ringgit",
        "Moroccan Dirham",
        "Norwegian Krone",
        "New Zealand Dollar",
        "Peruvian Nuevo Sol",
        "Philippine Peso",
        "Polish Zloty",
        "Romanian Leu",
        "Russian Ruble",
        "Swedish Krona",
        "Singapore Dollar",
        "Thai Baht",
        "Turkish Lira",
        "Taiwanese Dollar",
        "Silver (ounce)",
        "Gold (ounce)",
        "South African Rand"
    )
)

val currencyResponseResult = ResponseState.Result(testCurrencyResponse)