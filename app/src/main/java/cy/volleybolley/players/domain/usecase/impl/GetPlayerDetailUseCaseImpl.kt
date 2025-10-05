package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.GetPlayerDetailUseCase

class GetPlayerDetailUseCaseImpl(
    private val repository: PlayersRepository
) : GetPlayerDetailUseCase {
    override suspend fun invoke(playerId: Int): VolleyResult<PlayerDetail, ErrorType> =
        repository.getPlayerDetail(playerId)
}
