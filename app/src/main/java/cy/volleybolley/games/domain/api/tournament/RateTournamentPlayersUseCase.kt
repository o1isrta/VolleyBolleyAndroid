package cy.volleybolley.games.domain.api.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.RatePlayer

interface RateTournamentPlayersUseCase {
    suspend fun ratePlayers(tournamentId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType>
}
