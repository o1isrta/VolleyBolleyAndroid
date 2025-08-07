package cy.volleybolley.referencedata.domain.model

data class Country(
    val id: Int,
    val name: String? = null,
    val cities: List<City>? = null
)
