package com.dvt.core.mappers

import com.dvt.network.models.Currencies
import com.dvt.network.models.convert.ConversionResponse
import com.sammy.data.entity.RatesEntity

fun ConversionResponse.toRatesEntity(): List<RatesEntity> {
    var count:Int = 0
    val arrayList = ArrayList<RatesEntity>()
    this.rates.keys.forEach {
        val rate = rates[it]?.rate.toString()
        arrayList.add(RatesEntity(count,rate))
        count++
    }
    return arrayList
}