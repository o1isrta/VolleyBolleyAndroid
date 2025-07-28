package cy.volleybolley.referencedata.data.network

import cy.volleybolley.referencedata.data.dto.CountryDto
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface ReferenceDataResponse {
    @Serializable
    class CountriesResponse(
        val countries: List<CountryDto>
    ) : ReferenceDataResponse

    @Serializable
    class CurrencyResponse(
        @SerialName("currency_type")
        val type: String,
        @SerialName("currency_name")
        val name: String
    ) : ReferenceDataResponse

    @Serializable
    class FaqResponse(
        val faq: String
    ) : ReferenceDataResponse
}

fun ReferenceDataResponse.CurrencyResponse.mapToDomain(): Currency {
    return Currency(
        type = this.type,
        name = this.name
    )
}

fun ReferenceDataResponse.FaqResponse.mapToDomain(): Faq {
    return Faq(
        faq = this.faq
    )
}
