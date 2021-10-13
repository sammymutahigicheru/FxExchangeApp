package com.dvt.core

import com.dvt.core.helpers.getCountryCode
import com.dvt.core.helpers.getCountryCurrency
import com.google.common.truth.Truth
import org.junit.Test

class CurrencyUtilsTest {
    @Test
    fun `test given correct country code return correct currency` (){
        val countryCode = getCountryCode("Kenya")
        val countryCurrency = getCountryCurrency(countryCode)
        Truth.assertThat(countryCurrency).isEqualTo("KES")
    }
}