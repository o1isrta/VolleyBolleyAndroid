package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository


class GetPlayerDetailUseCase(
    private val repository: PlayersRepository
) {
    suspend operator fun invoke(playerId: Int): VolleyResult<PlayerDetail, Throwable> {
        return repository.getPlayerDetail(playerId)
    }
}
