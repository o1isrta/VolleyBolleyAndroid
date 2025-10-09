package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.EventFilter

interface GetGamesUseCase {
    suspend fun getGames(filter: EventFilter): VolleyResult<List<Event>, ErrorType>
}
