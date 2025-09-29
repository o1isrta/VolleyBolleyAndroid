package cy.volleybolley.referencedata.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CountryDto(
    @SerialName("country_id")
    val id: Int,
    @SerialName("country_name")
    val name: String,
    @SerialName("cities")
    val cities: List<CityDto>
)
