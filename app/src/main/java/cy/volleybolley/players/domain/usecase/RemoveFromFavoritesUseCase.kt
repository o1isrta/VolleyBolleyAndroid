package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.repository.PlayersRepository


class RemoveFromFavoritesUseCase(
    private val repository: PlayersRepository
) {
    suspend operator fun invoke(playerId: Int): VolleyResult<Unit, Throwable> {
        return repository.removeFromFavorites(playerId)
    }
}
