package cy.volleybolley.referencedata.domain.model

/**
 * Необходимо поправить под правильную логику справочников
 */
enum class CurrencyType(val currencyValue: String) {
    EUR("€"),
    THB("฿"),
    UNKNOWN("UNKNOWN");

    companion object {
        @JvmStatic
        fun getCurrencyByName(currencyName: String): CurrencyType {
            return entries.find { it.currencyValue == currencyName } ?: UNKNOWN
        }
    }
}
