package cy.volleybolley.referencedata.domain.model

data class Currency(
    val id: Int,
    val type: CurrencyType,
    val name: String,
    val countryId: Int
)
