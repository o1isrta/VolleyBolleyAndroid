package cy.volleybolley.games.data.dto

import cy.volleybolley.courts.data.dto.LocationDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GamePreviewDto (
    @SerialName("game_id") val gameId: Int,
    @SerialName("host") val host: HostDto,
    @SerialName("court_location") val locationDto: LocationDto,
    @SerialName("message") val message: String,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String,
)
