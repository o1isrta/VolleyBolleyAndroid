package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.Serializable

@Serializable
data class CityLocalDto(
    val id: Int,
    val name: String
)
