package cy.volleybolley.core

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {

    private val zone = ZoneId.systemDefault()

    private val dateFormatterDayMonth = DateTimeFormatter.ofPattern("dd MMMM")
    private val timeFormatterHhMm = DateTimeFormatter.ofPattern("hh:mm")

    fun formatRangeToDayAndPeriodTime(startIso: String, endIso: String): String {
        val startDate = Instant.parse(startIso).atZone(zone)
        val endDate = Instant.parse(endIso).atZone(zone)

        val date = startDate.format(dateFormatterDayMonth)
        val startRange = startDate.format(timeFormatterHhMm)
        val endRange = endDate.format(timeFormatterHhMm)

        return "$date, $startRange-$endRange"
    }
}
