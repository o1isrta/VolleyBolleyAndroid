package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface JoinTournamentUseCase {
    suspend fun joinTournament(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
