package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.GetAllPlayersUseCase

class GetAllPlayersUseCaseImpl(
    private val repository: PlayersRepository
) : GetAllPlayersUseCase {
    override suspend fun invoke(): VolleyResult<List<Player>, ErrorType> =
        repository.getAllPlayers()
}
