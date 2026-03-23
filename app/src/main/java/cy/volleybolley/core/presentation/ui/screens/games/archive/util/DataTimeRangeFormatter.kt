package cy.volleybolley.core.presentation.ui.screens.games.archive.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.max

object DataTimeRangeFormatter {
    fun format(startIso: String, endIso: String): Pair<String, String> {
        val locale = Locale.ENGLISH
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).apply {
            timeZone = TimeZone.getDefault()
        }
        val start = parser.parse(startIso)
        val end = parser.parse(endIso)
        val dateFmt = SimpleDateFormat("d MMMM", locale)
        val timeFmt = SimpleDateFormat("h:mm a", locale)
        val date = dateFmt.format(start ?: 0)
        val startTime = timeFmt.format(start ?: 0).lowercase(locale)
        val endTime = timeFmt.format(end ?: max(start?.time ?: 0, 0)).lowercase(locale)
        return date to "$startTime–$endTime"
    }
}
