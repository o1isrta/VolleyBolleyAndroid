package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame
import cy.volleybolley.games.domain.model.event.game.GameDetails

interface GamesRepository {
    suspend fun createGame(game: CreateGame): VolleyResult<CreatedGame, ErrorType>
    suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType>
    suspend fun cancelGame(gameId: Int): VolleyResult<Unit, ErrorType>
}
