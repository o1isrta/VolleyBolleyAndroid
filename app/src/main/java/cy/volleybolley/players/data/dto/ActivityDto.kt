package cy.volleybolley.players.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityDto(
    @SerialName("event_timestamp") val eventTimestamp: String,
    @SerialName("court_location") val courtLocation: LocationDto
)

