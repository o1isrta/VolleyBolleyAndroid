package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CurrencyLocalDto(
    @SerialName("id")
    val id: Int,
    @SerialName("type")
    val type: String,
    @SerialName("name")
    val name: String,
    @SerialName("country_id")
    val countryId: Int
)
