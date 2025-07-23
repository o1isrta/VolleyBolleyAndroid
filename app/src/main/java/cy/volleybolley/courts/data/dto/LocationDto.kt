package cy.volleybolley.courts.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("longitude") val longitude: Double,
    @SerialName("latitude") val latitude: Double,
    @SerialName("court_name") val courtName: String,
    @SerialName("location_name") val locationName: String,
)