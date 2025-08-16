package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository

class SearchPlayersUseCase(
    private val repository: PlayersRepository
) {
    suspend operator fun invoke(query: String): VolleyResult<List<Player>, Throwable> {
        return repository.searchPlayers(query)
    }
}
