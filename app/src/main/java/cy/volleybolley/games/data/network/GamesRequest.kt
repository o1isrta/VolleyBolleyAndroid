package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.GameDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.TournamentDto

sealed interface GamesRequest {
    class CreateGame(
        val path: String = GAMES,
        val game: GameDto,
    ) : GamesRequest

    class CreateTournament(
        val path: String = TOURNAMENTS,
        val tournament: TournamentDto,
    ) : GamesRequest

    class GetGameDetails(
        val gameId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId"
        }
    }

    class GetTournamentDetails(
        val tournamentId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId"
        }
    }

    class InvitePlayersToGame(
        val gameId: Int,
        val players: PlayersDto,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId/$INVITE_PLAYERS"
        }
    }

    class InvitePlayersToTournament(
        val tournamentId: Int,
        val players: PlayersDto,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$INVITE_PLAYERS"
        }
    }

    class GetPreview() : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$PREVIEW"
        }
    }

    class GetMyGames() : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$MY_GAMES"
        }
    }

    class GetArchive() : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$ARCHIVE"
        }
    }

    class GetInvites() : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$INVITES"
        }
    }

    class GetUpcoming() : GamesRequest{
        fun fullPath(): String {
            return "$GAMES/$UPCOMING"
        }
    }

    class JoinGame() : GamesRequest

    class JoinTournament() : GamesRequest

    class GetPlayersToRate() : GamesRequest

    class RatePlayers() : GamesRequest

    class SkipRating() : GamesRequest

    companion object {
        const val GAMES = "games"
        const val TOURNAMENTS = "tournaments"
        const val INVITE_PLAYERS = "invite-players"
        const val PREVIEW = "preview"
        const val MY_GAMES = "my-games"
        const val ARCHIVE = "archive"
        const val INVITES = "invites"
        const val UPCOMING = "upcoming"
    }
}
