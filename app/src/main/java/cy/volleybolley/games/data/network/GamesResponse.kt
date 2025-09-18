package cy.volleybolley.games.data.network

import cy.volleybolley.courts.data.dto.LocationDto
import cy.volleybolley.games.data.dto.GamePreviewDto
import cy.volleybolley.games.data.dto.HostDto
import cy.volleybolley.games.data.dto.PlayerShortDto
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
        @SerialName("court_location") val location: LocationDto,
    ) : GamesResponse

    @Serializable
    class GetGameDetails(
        @SerialName("game_id") val gameId: Int,
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

    class InvitePlayersToGame : GamesResponse

    @Serializable
    class GetPreview(
        @SerialName("upcoming_game_time") val upcomingGame: String,
        @SerialName("invites") val invites: Int,
    ) : GamesResponse

    @Serializable
    class GetMyGames(
        @SerialName("games") val games: List<GamePreviewDto>,
        @SerialName("tournaments") val tournaments: List<TournamentPreviewDto>,
    ) : GamesResponse

    @Serializable
    class GetArchive(
        @SerialName("games") val games: List<GamePreviewDto>,
        @SerialName("tournaments") val tournaments: List<TournamentPreviewDto>,
    ) : GamesResponse

    @Serializable
    class GetInvites(
        @SerialName("games") val games: List<GamePreviewDto>,
        @SerialName("tournaments") val tournaments: List<TournamentPreviewDto>,
    ) : GamesResponse

    @Serializable
    class GetUpcoming(
        @SerialName("games") val games: List<GamePreviewDto>,
        @SerialName("tournaments") val tournaments: List<TournamentPreviewDto>,
    ) : GamesResponse

    @Serializable
    class JoinGame(
        @SerialName("game_id") val gameId: Int,
        @SerialName("host") val host: HostDto,
        @SerialName("message") val message: String,
        @SerialName("start_time") val startTime: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("levels") val levels: List<String>,
        @SerialName("gender") val gender: String,
        @SerialName("is_joined") val isJoined: Boolean,
        @SerialName("is_private") val isPrivate: Boolean,
        @SerialName("currency_type") val currencyType: String,
        @SerialName("payment_type") val paymentType: String,
        @SerialName("payment_account") val paymentAccount: String,
        @SerialName("court_location") val courtLocation: LocationDto,
        @SerialName("game_type") val gameType: String,
        @SerialName("price_per_person") val pricePerPerson: String,
        @SerialName("maximum_players") val maximumPlayers: Int,
        @SerialName("players") val players: List<PlayerShortDto>,
    ) : GamesResponse

    class DeclineGameInvite : GamesResponse

    class CancelGame : GamesResponse

    @Serializable
    class GetPlayersToRate(
        @SerialName("players") val players: List<PlayerShortDto>,
    ) : GamesResponse

    class RatePlayers : GamesResponse

    class SkipRating : GamesResponse

}
