package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.AddToFavoritesUseCase

class AddToFavoritesUseCaseImpl(
    private val repository: PlayersRepository
) : AddToFavoritesUseCase {
    override suspend fun invoke(playerId: Int): VolleyResult<Player, ErrorType> =
        repository.addToFavorites(playerId)
}
