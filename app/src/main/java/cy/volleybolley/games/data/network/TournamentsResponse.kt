package cy.volleybolley.games.data.network

import cy.volleybolley.courts.data.dto.LocationDto
import cy.volleybolley.games.data.dto.HostDto
import cy.volleybolley.games.data.dto.PlayerShortDto
import cy.volleybolley.games.data.dto.ShortTeamDto
import cy.volleybolley.games.data.dto.TeamDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface TournamentsResponse {
    @Serializable
    class CreateTournament(
        @SerialName("tournament_id") val tournamentId: Int,
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
        @SerialName("payment_account") val paymentAccount: String?,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("teams") val teams: List<ShortTeamDto>,
        @SerialName("court_location") val location: LocationDto,
    ) : TournamentsResponse

    @Serializable
    class GetTournamentDetails(
        @SerialName("tournament_id") val tournamentId: Int,
        @SerialName("is_individual") val isIndividual: Boolean,
        @SerialName("host") val host: HostDto,
        @SerialName("message") val message: String,
        @SerialName("court_location") val courtLocation: LocationDto,
        @SerialName("start_time") val startTime: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("levels") val levels: List<String>,
        @SerialName("gender") val gender: String,
        @SerialName("price_per_person") val pricePerPerson: String,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("payment_type") val paymentType: String,
        @SerialName("payment_account") val paymentAccount: String,
        @SerialName("maximum_players") val maximumPlayers: Int,
        @SerialName("maximum_teams") val maximumTeams: Int,
        @SerialName("teams") val teams: List<TeamDto>,
    ) : TournamentsResponse

    class CancelTournament : TournamentsResponse

    class InvitePlayersToTournament : TournamentsResponse

    @Serializable
    class JoinTournament(
        @SerialName("tournament_id") val tournamentId: Int,
        @SerialName("tournament_type") val gameType: String,
        @SerialName("host") val host: HostDto,
        @SerialName("message") val message: String,
        @SerialName("start_time") val startTime: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("levels") val levels: List<String>,
        @SerialName("gender") val gender: String,
        @SerialName("is_joined") val isJoined: Boolean,
        @SerialName("is_individual") val isIndividual: Boolean,
        @SerialName("court_location") val courtLocation: LocationDto,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("payment_type") val paymentType: String,
        @SerialName("payment_account") val paymentAccount: String,
        @SerialName("price_per_person") val pricePerPerson: String,
        @SerialName("maximum_players") val maximumPlayers: Int,
        @SerialName("maximum_teams") val maximumTeams: Int,
        @SerialName("teams") val teams: List<TeamDto>,
    ) : TournamentsResponse

    class DeclineTournamentInvite : TournamentsResponse

    @Serializable
    class GetPlayersToRate(
        @SerialName("players") val players: List<PlayerShortDto>,
    ) : TournamentsResponse

    class RatePlayers : TournamentsResponse

    class SkipRating : TournamentsResponse
}
