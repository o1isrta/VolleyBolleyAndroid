package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.tournament.JoinedTournament

interface TournamentParticipationRepository {
    suspend fun invitePlayersToTournament(tournamentId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType>
    suspend fun joinTournament(tournamentId: Int): VolleyResult<JoinedTournament, ErrorType>
    suspend fun declineTournamentInvite(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
