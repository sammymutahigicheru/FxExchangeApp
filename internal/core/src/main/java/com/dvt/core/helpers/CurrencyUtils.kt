package com.dvt.core.helpers

import java.util.*

fun getCountryCurrency(countryCode: String?): String? {
    val availableLocales = Locale.getAvailableLocales()
    for (index in availableLocales.indices) {
        if (availableLocales[index].country == countryCode
        ) return Currency.getInstance(availableLocales[index]).currencyCode
    }
    return ""
}
fun getCountryCode(countryName: String) = Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

fun getAllCountries(): ArrayList<String> {

    val locales = Locale.getAvailableLocales()
    val countries = ArrayList<String>()
    for (locale in locales) {
        val country = locale.displayCountry
        if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
            countries.add(country)
        }
    }
    countries.sort()

    return countries
}