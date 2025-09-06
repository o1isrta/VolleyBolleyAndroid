package cy.volleybolley.core.presentation.ui.model

import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_APRIL
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_AUGUST
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_DECEMBER
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_FEBRUARY
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_JANUARY
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_JULY
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_JUNE
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_MARCH
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_MAY
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_NOVEMBER
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_OCTOBER
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_SEPTEMBER
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.INDEX_UNKNOWN

enum class VolleyMonths(val index: Int, val simpleName: String) {
    JANUARY(INDEX_JANUARY, "january"),
    FEBRUARY(INDEX_FEBRUARY, "february"),
    MARCH(INDEX_MARCH, "march"),
    APRIL(INDEX_APRIL, "april"),
    MAY(INDEX_MAY, "may"),
    JUNE(INDEX_JUNE, "june"),
    JULY(INDEX_JULY, "july"),
    AUGUST(INDEX_AUGUST, "august"),
    SEPTEMBER(INDEX_SEPTEMBER, "september"),
    OCTOBER(INDEX_OCTOBER, "october"),
    NOVEMBER(INDEX_NOVEMBER, "november"),
    DECEMBER(INDEX_DECEMBER, "december"),
    UNKNOWN(INDEX_UNKNOWN, "unknown");

    companion object {
        @JvmStatic
        fun getNameByIndex(index: Int): String {
            return VolleyMonths.entries.find { it.index == index }?.simpleName ?: UNKNOWN.simpleName
        }
    }
}
