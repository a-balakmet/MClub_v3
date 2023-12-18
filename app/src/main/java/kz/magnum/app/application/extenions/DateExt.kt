package kz.magnum.app.application.extenions

import aab.lib.commons.extensions.twoDigitsString
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale


fun LocalDate.toBirthday(): String = "${dayOfMonth.twoDigitsString()}.${monthValue.twoDigitsString()}.$year"

fun LocalDateTime.toBackEndPattern(dateTimeFormatter: DateTimeFormatter = ISO_LOCAL_DATE_TIME): String = this.format(dateTimeFormatter)

fun LocalDateTime.toStringDate(showYear: Boolean) = if (showYear) {
    "${dayOfMonth.twoDigitsString()}.${monthValue.twoDigitsString()}.${year}"
} else {
    "${dayOfMonth.twoDigitsString()}.${monthValue.twoDigitsString()}"
}

fun LocalDateTime.toLocalisedDate(locale: String): String {
    val date = "$dayOfMonth ${DateFormatSymbols(Locale(locale)).months[monthValue - 1]} $year "
    val localizedHour = when (locale) {
        "kk" -> "сағат"
        else -> "в"
    }
    val time = "$localizedHour ${hour.twoDigitsString()}:${minute.twoDigitsString()}"
    return "$date $time"
}

fun LocalDateTime.toShortLocalisedDate(locale: String) = "$dayOfMonth ${month.getDisplayName(TextStyle.SHORT, Locale(locale))}"

fun LocalDateTime.shortWeekDay(locale: String): String = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale(locale))

fun LocalDate.isWithinRange(start: LocalDate, end: LocalDate): Boolean = (isBefore(end) && isAfter(start))

fun LocalDate.localisedMonthName(locale: Locale, capitalised: Boolean): String {
    val date: Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
    val month = SimpleDateFormat("LLLL", locale).format(date)
    return if (capitalised) month.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } else month
}

// text = SimpleDateFormat("LLLL", Locale(viewModel.locale)).format(Date())
//                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },