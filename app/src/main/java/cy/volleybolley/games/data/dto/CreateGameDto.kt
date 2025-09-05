package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateGameDto(
    @SerialName("court_id") val courtId: Int,
    @SerialName("message") val message: String,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String,
    @SerialName("gender") val gender: String,
    @SerialName("levels") val levels: List<String>,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("maximum_players") val maximumPlayers: Int,
    @SerialName("price_per_person") val price: String,
    @SerialName("payment_type") val paymentType: String,
    @SerialName("players") val players: List<Int>,
)
