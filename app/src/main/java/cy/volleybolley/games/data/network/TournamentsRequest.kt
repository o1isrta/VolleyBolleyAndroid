package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.CreateTournamentDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.RatePlayerDto
import cy.volleybolley.games.data.network.GamesRequest.Companion.CANCEL
import cy.volleybolley.games.data.network.GamesRequest.Companion.INVITES
import cy.volleybolley.games.data.network.GamesRequest.Companion.INVITE_PLAYERS
import cy.volleybolley.games.data.network.GamesRequest.Companion.JOIN_TOURNAMENT
import cy.volleybolley.games.data.network.GamesRequest.Companion.RATE_PLAYERS
import cy.volleybolley.games.data.network.GamesRequest.Companion.SKIP
import cy.volleybolley.games.data.network.GamesRequest.Companion.TOURNAMENTS

sealed interface TournamentsRequest {

    class CreateTournament(
        val path: String = TOURNAMENTS,
        val tournament: CreateTournamentDto,
    ) : TournamentsRequest

    class GetTournamentDetails(
        val tournamentId: Int,
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId"
        }
    }

    class CancelTournament(
        val tournamentId: Int
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$CANCEL"
        }
    }

    class InvitePlayersToTournament(
        val tournamentId: Int,
        val players: PlayersDto,
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$INVITE_PLAYERS"
        }
    }

    class JoinTournament(
        val tournamentId: Int,
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$JOIN_TOURNAMENT"
        }
    }

    class DeclineTournamentInvite(
        val tournamentId: Int,
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$INVITES"
        }
    }

    class GetPlayersToRate(
        val tournamentId: Int,
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$RATE_PLAYERS"
        }
    }

    class RatePlayers(
        val tournamentId: Int,
        val players: List<RatePlayerDto>
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$RATE_PLAYERS"
        }
    }

    class SkipRating(
        val tournamentId: Int,
    ) : TournamentsRequest {
        fun fullPath(): String {
            return "$TOURNAMENTS/$tournamentId/$SKIP"
        }
    }
}
