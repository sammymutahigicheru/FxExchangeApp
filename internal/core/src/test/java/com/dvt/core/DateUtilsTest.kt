package com.dvt.core

import com.dvt.core.helpers.convertTimeStamp
import com.google.common.truth.Truth
import org.junit.Test

class DateUtilsTest {
    @Test
    fun `test convert time stamp works as expected` (){
        val currentTime:Long = 1633874421423
        val date = convertTimeStamp(currentTime)
        //2021-10-10
        Truth.assertThat(date).isEqualTo("2021-10-10")
    }
}