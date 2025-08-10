package cy.volleybolley.referencedata.data.dto

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
    val countryId: CountryIdDto
)
