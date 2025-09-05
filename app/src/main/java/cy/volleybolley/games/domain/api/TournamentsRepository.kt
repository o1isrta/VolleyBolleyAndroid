package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.tournament.CreateTournament
import cy.volleybolley.games.domain.model.event.tournament.CreatedTournament
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

interface TournamentsRepository {
    suspend fun createTournament(tournament: CreateTournament): VolleyResult<CreatedTournament, ErrorType>
    suspend fun getTournamentDetails(tournamentId: Int): VolleyResult<TournamentDetails, ErrorType>
    suspend fun cancelTournament(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
