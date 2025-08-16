package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository

class GetAllPlayersUseCase(
    private val repository: PlayersRepository
) {
    suspend operator fun invoke(): VolleyResult<List<Player>, Throwable> {
        return repository.getAllPlayers()
    }
}
