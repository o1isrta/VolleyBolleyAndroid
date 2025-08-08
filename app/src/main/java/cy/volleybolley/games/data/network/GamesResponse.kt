package cy.volleybolley.games.data.network

import cy.volleybolley.courts.data.dto.LocationDto
import cy.volleybolley.games.data.dto.GamePreviewDto
import cy.volleybolley.games.data.dto.HostDto
import cy.volleybolley.games.data.dto.PlayerShortDto
import cy.volleybolley.games.data.dto.TeamDto
import cy.volleybolley.games.data.dto.TeamShortDto
import cy.volleybolley.games.data.dto.TournamentPreviewDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface GamesResponse {
    @Serializable
    class CreateGame(
        @SerialName("game_id") val gameId: Int,
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
        @SerialName("payment_account") val paymentAccount: String?,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("players") val players: List<Int>,
    ) : GamesResponse

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
        @SerialName("teams") val teams: List<TeamShortDto>,
    ) : GamesResponse

    @Serializable
    class GetGameDetails(
        @SerialName("game_id") val gameId: Int,
        @SerialName("game_type") val gameType: String,
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
        @SerialName("players") val players: List<PlayerShortDto>,
    ) : GamesResponse

    @Serializable
    class GetTournamentDetails(
        @SerialName("tournament_id") val tournamentId: Int,
        @SerialName("is_individual") val isIndividual: Boolean,
        @SerialName("tournament_type") val gameType: String,
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
        @SerialName("teams") val players: List<TeamDto>,
    ) : GamesResponse

    class InvitePlayersToGame : GamesResponse

    class InvitePlayersToTournament : GamesResponse

    @Serializable
    class GetPreview(
        @SerialName("upcoming_game_time") val upcomingGame: String,
        @SerialName("invites") val invites: Int,
    ) : GamesResponse

    @Serializable
    class GetMyGames(
        @SerialName("games") val games: GamePreviewDto,
        @SerialName("tournaments") val tournaments: TournamentPreviewDto,
    ) : GamesResponse

    @Serializable
    class GetArchive(
        @SerialName("games") val games: GamePreviewDto,
        @SerialName("tournaments") val tournaments: TournamentPreviewDto,
    ) : GamesResponse

    @Serializable
    class GetInvites(
        @SerialName("games") val games: GamePreviewDto,
        @SerialName("tournaments") val tournaments: TournamentPreviewDto,
    ) : GamesResponse

    @Serializable
    class GetUpcoming(
        @SerialName("games") val games: GamePreviewDto,
        @SerialName("tournaments") val tournaments: TournamentPreviewDto,
    ) : GamesResponse

    @Serializable
    class JoinGame(
        @SerialName("game_id") val gameId: Int,
        @SerialName("is_joined") val isJoined: Boolean,
        @SerialName("is_private") val isPrivate: Boolean,
        @SerialName("court_location") val courtLocation: LocationDto,
        @SerialName("start_time") val startTime: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("levels") val levels: List<String>,
        @SerialName("gender") val gender: String,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("payment_type") val paymentType: String,
        @SerialName("payment_account") val paymentAccount: String,
        @SerialName("price_per_person") val pricePerPerson: String,
        @SerialName("maximum_players") val maximumPlayers: Int,
    ) : GamesResponse

    @Serializable
    class JoinTournament(
        @SerialName("tournament_id") val tournamentId: Int,
        @SerialName("is_joined") val isJoined: Boolean,
        @SerialName("is_individual") val isIndividual: Boolean,
        @SerialName("court_location") val courtLocation: LocationDto,
        @SerialName("start_time") val startTime: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("levels") val levels: List<String>,
        @SerialName("gender") val gender: String,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("payment_type") val paymentType: String,
        @SerialName("payment_account") val paymentAccount: String,
        @SerialName("price_per_person") val pricePerPerson: String,
        @SerialName("maximum_players") val maximumPlayers: Int,
        @SerialName("maximum_teams") val maximumTeams: Int,
    ) : GamesResponse

    class DeclineGameInvite : GamesResponse

    class DeclineTournamentInvite : GamesResponse

    @Serializable
    class GetPlayersToRate(
        @SerialName("players") val players: List<PlayerShortDto>,
    ) : GamesResponse

    class RatePlayers : GamesResponse

    class SkipRating : GamesResponse

}
