package cy.volleybolley.referencedata.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CountryIdDto(
    @SerialName("country_id")
    val id: Int
)
