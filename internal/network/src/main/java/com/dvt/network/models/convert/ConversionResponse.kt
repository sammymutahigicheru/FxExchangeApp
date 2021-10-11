package com.dvt.network.models.convert


data class ConversionResponse(
    val amount: String,
    val base_currency_code: String,
    val base_currency_name: String,
    var rates: HashMap<String, Rates> = HashMap(),
    val status: String,
    val updated_date: String
)