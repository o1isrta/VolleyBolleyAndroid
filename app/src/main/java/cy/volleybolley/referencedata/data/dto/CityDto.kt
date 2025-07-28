package cy.volleybolley.referencedata.data.dto

import cy.volleybolley.referencedata.domain.model.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    @SerialName("city_id")
    val id: Int,
    @SerialName("city_name")
    val name: String
)

fun CityDto.mapToDomain(): City {
    return City(
        id = id,
        name = name
    )
}
