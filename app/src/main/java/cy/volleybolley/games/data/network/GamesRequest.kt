package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.GameDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.RatePlayerDto
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

    class GetUpcoming() : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$UPCOMING"
        }
    }

    class JoinGame(
        val gameId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId/$JOIN_GAME"
        }
    }

    class JoinTournament(
        val tournamentId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$tournamentId/$JOIN_TOURNAMENT"
        }
    }

    class DeclineGameInvite(
        val gameId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId/$INVITES"
        }
    }

    class DeclineTournamentInvite(
        val tournamentId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$INVITES"
        }
    }

    class GetPlayersToRate(
        val gameId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId/$RATE_PLAYERS"
        }
    }

    class RatePlayers(
        val gameId: Int,
        val players: List<RatePlayerDto>
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId/$RATE_PLAYERS"
        }
    }

    class SkipRating(
        val gameId: Int,
    ) : GamesRequest {
        fun fullPath(): String {
            return "$GAMES/$gameId/$SKIP"
        }
    }

    companion object {
        const val GAMES = "games"
        const val TOURNAMENTS = "tournaments"
        const val INVITE_PLAYERS = "invite-players"
        const val PREVIEW = "preview"
        const val MY_GAMES = "my-games"
        const val ARCHIVE = "archive"
        const val INVITES = "invites"
        const val UPCOMING = "upcoming"
        const val JOIN_GAME = "join-game"
        const val JOIN_TOURNAMENT = "join-tournament"
        const val RATE_PLAYERS = "rate-players"
        const val SKIP = "skip"
    }
}
