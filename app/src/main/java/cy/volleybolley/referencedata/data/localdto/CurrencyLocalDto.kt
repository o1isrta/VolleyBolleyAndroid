package cy.volleybolley.referencedata.data.localdto

import cy.volleybolley.referencedata.domain.model.Currency
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyLocalDto(
    val id: Int,
    val type: String,
    val name: String,
    val country: CountryLocalDto
)

fun CurrencyLocalDto.mapToDomain(): Currency {
    return Currency(
        id = id,
        type = type,
        name = name,
        country = country.mapToDomain()
    )
}

fun Currency.mapToLocalDto(): CurrencyLocalDto {
    return CurrencyLocalDto(
        id = id,
        type = type,
        name = name,
        country = country.mapToLocalDto()
    )
}

fun List<CurrencyLocalDto>.mapToDomain(): List<Currency> {
    return this.map { it -> it.mapToDomain() }
}

fun List<Currency>.mapToLocalDto(): List<CurrencyLocalDto> {
    return this.map { it -> it.mapToLocalDto() }
}
