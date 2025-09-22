package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CityLocalDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
