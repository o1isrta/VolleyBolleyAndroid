package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.tournament.JoinTournamentUseCase
import cy.volleybolley.games.domain.api.TournamentsRepository

class JoinTournamentUseCaseImpl(private val repository: TournamentsRepository): JoinTournamentUseCase {
    override suspend fun joinTournament(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        return repository.joinTournament(tournamentId)
    }
}
