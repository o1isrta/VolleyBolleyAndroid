package cy.volleybolley.referencedata.data.network

import cy.volleybolley.referencedata.data.dto.CountryDto
import cy.volleybolley.referencedata.data.dto.CurrencyDto
import cy.volleybolley.referencedata.data.dto.FaqDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface ReferenceDataResponse {
    @Serializable
    class CountriesResponse(
        @SerialName("countries")
        val countries: List<CountryDto>
    ) : ReferenceDataResponse

    @Serializable
    class CurrenciesResponse(
        @SerialName("currencies")
        val currencies: List<CurrencyDto>
    ) : ReferenceDataResponse

    @Serializable
    class FaqResponse(
        @SerialName("faq")
        val faqDto: FaqDto
    ) : ReferenceDataResponse
}
