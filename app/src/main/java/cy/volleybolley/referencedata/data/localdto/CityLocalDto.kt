package cy.volleybolley.referencedata.data.localdto

import cy.volleybolley.referencedata.domain.model.City
import kotlinx.serialization.Serializable

@Serializable
data class CityLocalDto(
    val id: Int,
    val name: String
)

fun CityLocalDto.mapToDomain(): City {
    return City(
        id = id,
        name = name
    )
}

fun City.mapToLocalDto(): CityLocalDto {
    return CityLocalDto(
        id = id,
        name = name
    )
}
