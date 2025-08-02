package cy.volleybolley.core.presentation.ui.model

class VolleyTimeStamp(
    val hour: Int,
    val minutes: Int,
    val isAfternoon: Boolean,
) {
    val hour12Format: Int
        get() = hour - if (isAfternoon) AFTERNOON_VALUE else 0

    fun getTimeString24HourFormat() = "$hour:${getCorrectMinutesString()}"

    fun getAfternoonMark() = if (isAfternoon) PM_MARK else AM_MARK

    fun getCorrectTimeString() = "$hour12Format:${getCorrectMinutesString()}"

    private fun getCorrectMinutesString() = "%02d".format(minutes)

    companion object {
        const val AFTERNOON_VALUE: Int = 12
        const val PM_MARK = "PM"
        const val AM_MARK = "AM"
    }
}
