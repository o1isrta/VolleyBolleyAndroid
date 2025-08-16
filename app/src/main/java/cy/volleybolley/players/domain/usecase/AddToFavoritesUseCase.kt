package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository

class AddToFavoritesUseCase(
    private val repository: PlayersRepository
) {
    suspend operator fun invoke(playerId: Int): VolleyResult<Player, Throwable> {
        return repository.addToFavorites(playerId)
    }
}
