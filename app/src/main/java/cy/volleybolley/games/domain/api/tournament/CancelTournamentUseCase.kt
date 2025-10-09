package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface CancelTournamentUseCase {
    suspend fun cancelTournament(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
