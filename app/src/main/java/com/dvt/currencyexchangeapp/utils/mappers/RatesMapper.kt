package com.dvt.currencyexchangeapp.utils.mappers

import com.dvt.currencyexchangeapp.model.Rates
import com.dvt.network.models.convert.ConversionResponse
import com.sammy.data.entity.RatesEntity

internal fun ConversionResponse.toRates(): List<Rates> {
    val rates = this.rates
    val arrayList = ArrayList<Rates>()
    var count:Int = 1
    this.rates.keys.forEach {
        val rate = rates[it]?.rate
        arrayList.add(Rates(count,rates = rate!!))
        count++
    }
    return arrayList
}

internal fun List<RatesEntity>.toRate(): List<Rates> {
    val arrayList = ArrayList<Rates>()
    this.forEach {
        arrayList.add(
            Rates(
                it.id,
                it.rates
            )
        )
    }
    return arrayList
}


