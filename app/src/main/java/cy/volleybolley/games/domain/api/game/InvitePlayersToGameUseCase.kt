package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort

interface InvitePlayersToGameUseCase {
    suspend fun invitePlayersToGame(gameId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType>
}
