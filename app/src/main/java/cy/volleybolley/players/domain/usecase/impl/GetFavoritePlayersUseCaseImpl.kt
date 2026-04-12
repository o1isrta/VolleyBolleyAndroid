package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.GetFavoritePlayersUseCase

class GetFavoritePlayersUseCaseImpl(
    private val repository: PlayersRepository
) : GetFavoritePlayersUseCase {
    override suspend fun invoke(): VolleyResult<List<Player>, ErrorType> =
        repository.getFavoritePlayers()
}
