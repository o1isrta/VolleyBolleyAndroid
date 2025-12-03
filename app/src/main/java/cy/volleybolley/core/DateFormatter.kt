package cy.volleybolley.core

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {

    private val zone = ZoneId.systemDefault()

    private val dateFormatterDayMonth = DateTimeFormatter.ofPattern("dd MMMM")
    private val timeFormatterHhMm = DateTimeFormatter.ofPattern("hh:mm")

    fun formatRangeToDayAndPeriodTime(startIso: String, endIso: String): String {
        val start = Instant.parse(startIso).atZone(zone)
        val end = Instant.parse(endIso).atZone(zone)

        val date = start.format(dateFormatterDayMonth)
        val from = start.format(timeFormatterHhMm)
        val to = end.format(timeFormatterHhMm)

        return "$date, $from-$to"
    }
}
