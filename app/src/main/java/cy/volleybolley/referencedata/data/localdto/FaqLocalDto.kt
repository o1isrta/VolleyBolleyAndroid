package cy.volleybolley.referencedata.data.localdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FaqLocalDto(
    @SerialName("faq")
    val faq: String
)
