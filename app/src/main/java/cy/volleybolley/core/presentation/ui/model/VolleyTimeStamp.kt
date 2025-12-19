package cy.volleybolley.core.presentation.ui.model

class VolleyTimeStamp(
    val hour: Int,
    val minutes: Int,
    val isAfternoon: Boolean,
) {
    private val hour12Format: Int
        get() = hour // hour - if (isAfternoon) AFTERNOON_VALUE else 0

    fun getTimeString24HourFormat() = "$hour:${getCorrectMinutesString()}"

    fun getAfternoonMark() = if (isAfternoon) PM_MARK else AM_MARK

    fun getCorrectTimeString() = "$hour12Format:${getCorrectMinutesString()}"

    private fun getCorrectMinutesString() = "%02d".format(minutes)

    companion object {
        const val AFTERNOON_VALUE: Int = 12
        const val PM_MARK = "PM"
        const val AM_MARK = "AM"
        const val HOUR = 60
    }

    /**
     * Метод для сравнения двух временных меток.
     * Возвращает отрицательное значение, если текущее время меньше,
     * ноль, если они равны, и положительное, если текущее время больше.
     */
    fun compareTo(other: VolleyTimeStamp): Int {
        val thisTotalMinutes = (hour + if (isAfternoon) AFTERNOON_VALUE else 0) * HOUR + minutes
        val otherTotalMinutes = (other.hour + if (other.isAfternoon) AFTERNOON_VALUE else 0) * HOUR + other.minutes
        return thisTotalMinutes - otherTotalMinutes
    }

    override fun toString(): String {
        return "VolleyTimeStamp(hour=$hour, minutes=$minutes, isAfternoon=$isAfternoon)"
    }
}
