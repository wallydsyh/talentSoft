package com.example.talentsofttestwallyd.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    private fun format(date: Date?, format: String): String {
        return if (date == null) "" else SimpleDateFormat(
            format,
            Locale.getDefault()
        ).format(date)
    }

    fun parseDate(dateInString: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var date: Date? = null
        try {
            date = formatter.parse(
                dateInString.replace(
                    "Z$".toRegex(),
                    "+0000"
                )
            )
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return """published the: ${format(date, "yyyy-MM-dd")}"""
    }
}