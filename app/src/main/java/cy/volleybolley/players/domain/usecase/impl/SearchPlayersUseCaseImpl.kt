package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.SearchPlayersUseCase

class SearchPlayersUseCaseImpl(
    private val repository: PlayersRepository
) : SearchPlayersUseCase {
    override suspend fun invoke(query: String): VolleyResult<List<Player>, ErrorType> =
        repository.searchPlayers(query)
}
