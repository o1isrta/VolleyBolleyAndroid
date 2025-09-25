package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.RemoveFromFavoritesUseCase

class RemoveFromFavoritesUseCaseImpl(
    private val repository: PlayersRepository
) : RemoveFromFavoritesUseCase {
    override suspend fun invoke(playerId: Int): VolleyResult<Unit, ErrorType> =
        repository.removeFromFavorites(playerId)
}
