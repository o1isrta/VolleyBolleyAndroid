package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer

interface TournamentRatingRepository {
    suspend fun getPlayersToRate(tournamentId: Int): VolleyResult<List<PlayerShort>, ErrorType>
    suspend fun ratePlayers(tournamentId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType>
    suspend fun skipRating(tournamentId: Int): VolleyResult<Unit, ErrorType>
}
