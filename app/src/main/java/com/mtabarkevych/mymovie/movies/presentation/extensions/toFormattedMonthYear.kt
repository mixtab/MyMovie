package com.mtabarkevych.mymovie.movies.presentation.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale


fun String.toFormattedMonthYear(): String {
    return try {
        val date = LocalDate.parse(this)
        val formatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH)
        date.format(formatter)
    } catch (e: Exception) {
        "Unknown"
    }
}
