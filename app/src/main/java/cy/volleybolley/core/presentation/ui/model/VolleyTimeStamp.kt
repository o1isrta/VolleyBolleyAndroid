package cy.volleybolley.core.presentation.ui.model

class VolleyTimeStamp(
    hourValue: Int,
    minutesValue: Int,
    isAfternoonValue: Boolean,
) {
    private val _hour = hourValue
    private val _minutes = minutesValue
    private val _isAfternoon = isAfternoonValue

    val hour: Int
        get() = _hour - if (_isAfternoon) AFTERNOON_VALUE else 0

    val minutes: Int
        get() = _minutes

    val isAfternoon: Boolean
        get() = _isAfternoon

    fun getTimeString24HourFormat(): String {
        return "$_hour:${getCorrectMinutesString()}"
    }

    fun getAfternoonMark(): String {
        return if (isAfternoon) PM_MARK else AM_MARK
    }

    fun getCorrectTimeString(): String {
        return "$hour:${getCorrectMinutesString()}"

    }

    private fun getCorrectMinutesString(): String {
        return if (minutes < DOUBLE_SYMBOL_MARK) {
            "0$minutes"
        } else {
            minutes.toString()
        }
    }

    companion object {
        const val AFTERNOON_VALUE: Int = 12
        const val DOUBLE_SYMBOL_MARK: Int = 10
        const val PM_MARK = "PM"
        const val AM_MARK = "AM"
        const val DURATION_FIELD_HINT = "_:__"
    }
}
