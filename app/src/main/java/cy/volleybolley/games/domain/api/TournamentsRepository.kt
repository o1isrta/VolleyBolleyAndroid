package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.tournament.CreateTournament
import cy.volleybolley.games.domain.model.event.tournament.CreatedTournament
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.event.tournament.JoinedTournament
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

interface TournamentsRepository {
    suspend fun createTournament(tournament: CreateTournament): VolleyResult<CreatedTournament, ErrorType>
    suspend fun getTournamentDetails(tournamentId: Int): VolleyResult<TournamentDetails, ErrorType>
    suspend fun invitePlayersToTournament(tournamentId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType>
    suspend fun joinTournament(tournamentId: Int): VolleyResult<JoinedTournament, ErrorType>
    suspend fun declineTournamentInvite(tournamentId: Int): VolleyResult<Unit, ErrorType>
    suspend fun getPlayersToRate(tournamentId: Int): VolleyResult<List<PlayerShort>, ErrorType>
    suspend fun ratePlayers(tournamentId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType>
    suspend fun skipRating(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
