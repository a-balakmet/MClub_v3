package kz.magnum.app.application.extenions

import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import java.text.DateFormatSymbols
import java.util.Locale

fun String.formatPhone(): String = when (length) {
    0 -> this
    1,2 -> String.format("(%s", this)
    3 -> String.format("(%s)", this)
    4,5,6 -> String.format("(%s) %s", substring(0, 3), substring(3, length))
    7,8 -> String.format("(%s) %s-%s", substring(0, 3), substring(3, 6), substring(6, length))
    9,10 -> String.format("(%s) %s-%s-%s", substring(0, 3), substring(3, 6), substring(6, 8), substring(8, length))
    else -> String.format("(%s) %s-%s-%s", substring(0, 3), substring(3, 6), substring(6, 8), substring(8, 9))
}

fun String.toChunked(divider: Int) =
    this.chunked(divider).toString()
        .replace("[", "")
        .replace(",", "")
        .replace("]", "")

fun String.toLocalisedDate(locale: String): String {
    val dateTime = this.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter)
    return "${dateTime.dayOfMonth} ${DateFormatSymbols(Locale(locale)).months[dateTime.monthValue - 1]} ${dateTime.year}"
}