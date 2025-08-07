package cy.volleybolley.referencedata.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FaqDto(
    @SerialName("faq")
    val faq: String
)
