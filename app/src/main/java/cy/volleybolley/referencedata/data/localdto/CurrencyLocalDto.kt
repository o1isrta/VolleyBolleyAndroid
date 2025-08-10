package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyLocalDto(
    val id: Int,
    val type: String,
    val name: String,
    val countryId: Int
)
