package cy.volleybolley.referencedata.data.dto

import cy.volleybolley.referencedata.domain.model.Currency
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    @SerialName("currency_id")
    val id: Int,
    @SerialName("currency_type")
    val type: String,
    @SerialName("currency_name")
    val name: String,
    @SerialName("country")
    val country: CountryDto
)

fun CurrencyDto.mapToDomain(): Currency {
    return Currency(
        id = id,
        type = type,
        name = name,
        country = country.mapToDomain()
    )
}

fun List<CurrencyDto>.mapToDomain(): List<Currency> {
    return this.map { it.mapToDomain() }
}
