package cy.volleybolley.referencedata.domain.model

data class Country(
    val id: Int,
    val name: String,
    val cities: List<City>
)
