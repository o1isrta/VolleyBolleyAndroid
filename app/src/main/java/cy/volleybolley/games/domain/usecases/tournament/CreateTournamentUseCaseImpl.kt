package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.tournament.CreateTournamentUseCase
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.model.event.tournament.CreateTournament
import cy.volleybolley.games.domain.model.event.tournament.CreatedTournament

class CreateTournamentUseCaseImpl(private val repository: TournamentsRepository) : CreateTournamentUseCase {
    override suspend fun createTournament(tournament: CreateTournament): VolleyResult<CreatedTournament, ErrorType> {
        return repository.createTournament(tournament)
    }
}
