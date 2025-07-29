package cy.volleybolley.courts.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CourtDto(
    @SerialName("court_id") val courtId: Int,
    @SerialName("price_description") val price: String?,
    @SerialName("description") val description: String?,
    @SerialName("contact_list") val contacts: List<ContactDto>?,
    @SerialName("photo_url") val photo: String?,
    @SerialName("tags") val tags: List<String>?,
    @SerialName("location") val location: LocationDto,
)
