package com.dvt.network.models.convert


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyConversionResponse(
    @Json(name = "from")
    val from: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "timestamp")
    val timestamp: Int,
    @Json(name = "to")
    val to: String,
    @Json(name = "total")
    val total: Double
)