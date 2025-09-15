package cy.volleybolley.referencedata.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CityDto(
    @SerialName("city_id")
    val id: Int,
    @SerialName("city_name")
    val name: String
)
