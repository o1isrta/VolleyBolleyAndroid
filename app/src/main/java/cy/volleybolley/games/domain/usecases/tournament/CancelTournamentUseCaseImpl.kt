package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.api.tournament.CancelTournamentUseCase

class CancelTournamentUseCaseImpl(
    private val repository: TournamentsRepository
) : CancelTournamentUseCase {
    override suspend fun cancelTournament(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        return repository.cancelTournament(tournamentId = tournamentId)
    }
}
