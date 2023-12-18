package kz.magnum.app.application

import java.time.format.DateTimeFormatter

object AppConstants {
    val phoneRegex = ("[\\s ()-/+]").toRegex()
    val namesRegex = "^[a-zA-Zа-яА-Я әғқңөұүhіӘҒҚҢӨҰҮҺІ-]+$".toRegex()
    var transactionsDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
}