package com.dvt.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.getInitials(): String {
    return if (this.length > 2) {
        this.substring(0, 2)
    } else {
        ""
    }
}

fun String.capitaliseName(): String {
    val parts = trim().split(" ").toMutableList()
    val first: String
    var last = ""

    if (parts.size > 1) {
        first =
            parts[0].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        last =
            parts[1].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } else {
        first = parts[0].substring(0, 2)
    }

    return ("$first $last")
}
fun String.convertToMonthDay(): String {
    //30-05-2021

    val epoch: Long

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    epoch = dateFormat.parse(this).time

    val newFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    return newFormat.format(Date(epoch))
}