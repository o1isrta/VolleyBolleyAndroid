package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CountryLocalDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("cities")
    val cities: List<CityLocalDto>
)
