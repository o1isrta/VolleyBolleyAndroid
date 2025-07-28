package cy.volleybolley.referencedata.data.dto

import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    @SerialName("country_id")
    val id: Int,
    @SerialName("country_name")
    val name: String? = null,
    val cities: List<CityDto>? = null
)
