package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.tournament.CreateTournament
import cy.volleybolley.games.domain.model.event.tournament.CreatedTournament

interface CreateTournamentUseCase {
    suspend fun createTournament(tournament: CreateTournament): VolleyResult<CreatedTournament, ErrorType>
}
