package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GameParticipationRepository
import cy.volleybolley.games.domain.api.game.InvitePlayersToGameUseCase
import cy.volleybolley.games.domain.model.entity.PlayerShort

class InvitePlayersToGameUseCaseImpl(
    private val repository: GameParticipationRepository
) : InvitePlayersToGameUseCase {
    override suspend fun invitePlayersToGame(
        gameId: Int,
        players: List<PlayerShort>
    ): VolleyResult<Unit, ErrorType> {
        return repository.invitePlayersToGame(gameId = gameId, players = players)
    }
}
