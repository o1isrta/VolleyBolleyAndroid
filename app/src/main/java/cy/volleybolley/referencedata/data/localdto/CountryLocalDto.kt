package cy.volleybolley.referencedata.data.localdto

import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.serialization.Serializable

@Serializable
data class CountryLocalDto(
    val id: Int,
    val name: String? = null,
    val cities: List<CityLocalDto>? = null
)

fun CountryLocalDto.mapToDomain(): Country {
    return Country(
        id = id,
        name = name,
        cities = cities?.map { it -> it.mapToDomain() }
    )
}

fun Country.mapToLocalDto(): CountryLocalDto {
    return CountryLocalDto(
        id = id,
        name = name,
        cities = cities?.map { it -> it.mapToLocalDto() }
    )
}

fun List<CountryLocalDto>.mapToDomain(): List<Country> {
    return this.map { it.mapToDomain() }
}

fun List<Country>.mapToLocalDto(): List<CountryLocalDto> {
    return this.map { it -> it.mapToLocalDto() }
}
