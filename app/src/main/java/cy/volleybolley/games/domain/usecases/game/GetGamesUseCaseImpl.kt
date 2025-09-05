package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.mapSuccess
import cy.volleybolley.games.domain.api.GameFeedRepository
import cy.volleybolley.games.domain.api.game.GetGamesUseCase
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.EventFilter

class GetGamesUseCaseImpl(
    private val repository: GameFeedRepository,
) : GetGamesUseCase {
    override suspend fun getGames(filter: EventFilter): VolleyResult<List<Event>, ErrorType> {
        return when (filter) {
            EventFilter.MY_GAMES -> repository.getMyGames().mapSuccess { events -> events.sortedBy { it.startTime } }
            EventFilter.UPCOMING -> repository.getUpcoming().mapSuccess { events -> events.sortedBy { it.startTime } }
            EventFilter.ARCHIVE -> repository.getArchive().mapSuccess { events -> events.sortedBy { it.startTime } }
            EventFilter.INVITES -> repository.getArchive().mapSuccess { events -> events.sortedBy { it.startTime } }
        }
    }
}
