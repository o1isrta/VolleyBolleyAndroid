package cy.volleybolley.referencedata.domain.model

enum class CurrencyType(val currencyValue: String) {
    EUR("EUR"),
    THB("THB"),
    UNKNOWN("UNKNOWN");

    companion object{
        @JvmStatic
        fun getCurrencyByName(currencyName: String): CurrencyType {
            return entries.find { it.currencyValue == currencyName } ?: UNKNOWN
        }
    }
}


