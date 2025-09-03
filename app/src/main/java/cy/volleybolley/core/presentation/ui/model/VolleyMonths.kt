package cy.volleybolley.core.presentation.ui.model


enum class VolleyMonths(val index: Int, val simpleName: String) {
    JANUARY(0, "january"),
    FEBRUARY(1, "february"),
    MARCH(2, "march"),
    APRIL(3, "april"),
    MAY(4, "may"),
    JUNE(5, "june"),
    JULY(6, "july"),
    AUGUST(7, "august"),
    SEPTEMBER(8, "september"),
    OCTOBER(9, "october"),
    NOVEMBER(10, "november"),
    DECEMBER(11, "december"),
    UNKNOWN(-1, "unknown");

    companion object {
        @JvmStatic
        fun getNameByIndex(index: Int): String {
            return VolleyMonths.entries.find { it.index == index }?.simpleName ?: UNKNOWN.simpleName
        }
    }
}
