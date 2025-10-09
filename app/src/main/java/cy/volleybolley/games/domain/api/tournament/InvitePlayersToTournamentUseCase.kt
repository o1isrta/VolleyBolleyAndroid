package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort

interface InvitePlayersToTournamentUseCase {
    suspend fun invitePlayersToTournament(tournamentId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType>
}
