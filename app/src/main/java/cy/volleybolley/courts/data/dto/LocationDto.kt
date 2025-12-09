package cy.volleybolley.courts.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LocationDto(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("court_name") val courtName: String,
    @SerialName("location_name") val locationName: String,
)
