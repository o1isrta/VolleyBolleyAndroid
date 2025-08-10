package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.Serializable

@Serializable
data class CountryLocalDto(
    val id: Int,
    val name: String,
    val cities: List<CityLocalDto>
)
