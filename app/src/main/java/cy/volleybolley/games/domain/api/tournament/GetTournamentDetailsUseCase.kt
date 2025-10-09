package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

interface GetTournamentDetailsUseCase {
    suspend fun getTournamentDetails(tournamentId: Int): VolleyResult<TournamentDetails, ErrorType>
}
