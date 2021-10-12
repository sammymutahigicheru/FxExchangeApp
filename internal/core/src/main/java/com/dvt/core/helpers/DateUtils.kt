package com.dvt.core.helpers

import java.text.SimpleDateFormat
import java.util.*

fun convertTimeStamp(currentTime: Long): String {

//    2020-05-27

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return dateFormat.format(Date(currentTime))
}