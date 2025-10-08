package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.game.JoinedGame

interface GameParticipationRepository {
    suspend fun invitePlayersToGame(gameId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType>
    suspend fun joinGame(gameId: Int): VolleyResult<JoinedGame, ErrorType>
    suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType>
}
