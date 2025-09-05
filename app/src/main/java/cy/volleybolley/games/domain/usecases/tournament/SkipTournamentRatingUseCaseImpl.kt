package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.api.tournament.SkipTournamentRatingUseCase

class SkipTournamentRatingUseCaseImpl(
    private val repository: TournamentsRepository
) : SkipTournamentRatingUseCase {
    override suspend fun skipRating(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        return repository.skipRating(tournamentId = tournamentId)
    }
}
