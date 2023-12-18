package kz.magnum.app.ui.components.transactions.lists

import aab.lib.commons.domain.models.QueryModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.magnum.app.application.AppConstants.transactionsDateTimeFormatter
import kz.magnum.app.application.extenions.toBackEndPattern
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.models.MonthAndDays
import kz.magnum.app.domain.models.transactions.Transaction
import kz.magnum.app.domain.useCase.pagesUseCase.PageUseCase
import kz.magnum.app.ui.builders.baseViewModels.PagedListViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale
import java.util.stream.Collectors
import java.util.stream.IntStream


class TransactionsViewModel(useCase: PageUseCase<Transaction>) : PagedListViewModel<Transaction>(useCase) {

    val start = MutableStateFlow<LocalDateTime>(LocalDateTime.now().minusMonths(1))
    val end = MutableStateFlow<LocalDateTime>(LocalDateTime.now())

    override var queriesList: List<QueryModel>? = listOf(
        QueryModel("dt_created_from", start.value.toBackEndPattern(transactionsDateTimeFormatter)),
        QueryModel("dt_created_to", end.value.toBackEndPattern(transactionsDateTimeFormatter))
    )

    var locale = "ru"
    private var firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
    val weekDays: List<DayOfWeek> = IntStream.range(0, 7)
        .mapToObj { l: Int -> firstDayOfWeek.plus(l.toLong()) }
        .collect(Collectors.toList())
    val totalDates: MutableList<MonthAndDays> = ArrayList()


    init {
        viewModelScope.launch {
            locale = dataStore.readPreference(AppStoreKeys.locale, "ru")
            /**
             * Re-initialization of [firstDayOfWeek] is not necessary for the purposes of this application.
             * The idea may be applied to build calendars of two types that depend on [locale]:
             * - week starts from Monday or
             * - week starts from Sunday
             * */
            firstDayOfWeek = WeekFields.of(Locale(locale)).firstDayOfWeek
            inflateDatesList()
        }
        onInit()
    }

    override fun onRefresh() {
        start.value = LocalDateTime.now().minusMonths(1)
        end.value = LocalDateTime.now()
    }

    fun updateDates(s: LocalDateTime, e: LocalDateTime) {
        this.start.value = s
        this.end.value = e
        queriesList = listOf(
            QueryModel("dt_created_from", s.toBackEndPattern(transactionsDateTimeFormatter)),
            QueryModel("dt_created_to", e.toBackEndPattern(transactionsDateTimeFormatter))
        )
        onEvent(
            event = ListUIEvent.DropQueries(
                defaultQueries =
                listOf(
                    QueryModel("dt_created_from", s.toBackEndPattern(transactionsDateTimeFormatter)),
                    QueryModel("dt_created_to", e.toBackEndPattern(transactionsDateTimeFormatter))
                )
            )
        )
    }

    private fun inflateDatesList() {

        fun getDaysByWeeks(date: LocalDate): List<List<LocalDate?>> {
            val daysInMonth: IntRange = (1..date.month.length(date.isLeapYear))
            val monthDates: List<LocalDate> = daysInMonth.map { date.withDayOfMonth(it) }
            val daysItems: MutableList<LocalDate?> = monthDates.toMutableList()
            daysItems.first().let {
                val dayOfWeek = if (firstDayOfWeek.value == 1) {
                    if (it!!.dayOfWeek.value == 0) 7 else it.dayOfWeek.value
                } else {
                    /**
                     * This clause may be applied for a calendar when week starts from Sunday,
                     * for example if [locale] = [Locale.CANADA_FRENCH].
                     * In this application this clause will never trigger.
                     */
                    if (it!!.dayOfWeek.value == 0) 1 else it.dayOfWeek.value + 1
                }
                (1 until dayOfWeek).forEach { _ -> daysItems.add(0, null) }
            }
            val daysByWeeks: List<MutableList<LocalDate?>> = daysItems.chunked(7) { it.toMutableList() }
            daysByWeeks.last().let { (1..7 - it.size).forEach { _ -> daysByWeeks.last().add(null) } }
            return daysByWeeks
        }

        val months: MutableList<LocalDate> = ArrayList()
        var start = YearMonth.of(minDate.year, minDate.month)
        val end = YearMonth.of(maxDate.year, maxDate.month)
        while (!start.isAfter(end)) {
            months.add(LocalDate.of(start.year, start.month, 1))
            start = start.plusMonths(1)
        }
        for (i in months) {
            totalDates.add(MonthAndDays(i, getDaysByWeeks(i)))
        }
    }

    companion object {
        var minDate: LocalDate = LocalDate.now().minusYears(3)
        var maxDate: LocalDate = LocalDate.now()
    }
}