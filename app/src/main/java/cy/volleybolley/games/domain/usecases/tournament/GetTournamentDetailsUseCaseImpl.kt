package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.api.tournament.GetTournamentDetailsUseCase
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

class GetTournamentDetailsUseCaseImpl(
    private val repository: TournamentsRepository
) : GetTournamentDetailsUseCase {
    override suspend fun getTournamentDetails(tournamentId: Int): VolleyResult<TournamentDetails, ErrorType> {
        return repository.getTournamentDetails(tournamentId)
    }
}
