package kz.magnum.app.domain.models

import java.time.LocalDate

data class MonthAndDays(
    val month: LocalDate,
    val days: List<List<LocalDate?>>
)