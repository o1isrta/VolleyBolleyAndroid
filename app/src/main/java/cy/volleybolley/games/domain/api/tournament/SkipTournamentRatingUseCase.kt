package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface SkipTournamentRatingUseCase {
    suspend fun skipRating(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
