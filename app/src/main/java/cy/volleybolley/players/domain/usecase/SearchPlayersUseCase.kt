package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player

fun interface SearchPlayersUseCase {
    suspend operator fun invoke(query: String, favoritesOnly: Boolean): VolleyResult<List<Player>, ErrorType>
}
