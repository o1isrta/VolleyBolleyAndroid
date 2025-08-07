package cy.volleybolley.courts.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ContactDto(
    @SerialName("contact_type") val contactType: String,
    @SerialName("contact") val contact: String,
)
