package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TournamentDto(
    @SerialName("court_id") val courtId: Int,
    @SerialName("message") val message: String,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String,
    @SerialName("is_individual") val isIndividual: Boolean,
    @SerialName("gender") val gender: String,
    @SerialName("levels") val levels: List<String>,
    @SerialName("maximum_players") val maximumPlayers: Int,
    @SerialName("maximum_teams") val maximumTeams: Int,
    @SerialName("price_per_person") val price: String,
    @SerialName("payment_type") val paymentType: String,
    @SerialName("teams") val teams: List<PlayersDto>,
)

