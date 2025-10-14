package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GameRatingRepository
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.api.game.SkipRatingUseCase
import cy.volleybolley.games.domain.model.event.EventType

class SkipRatingUseCaseImpl(
    private val gameRepository: GameRatingRepository,
    private val tournamentRepository: TournamentRatingRepository
) : SkipRatingUseCase {
    override suspend fun skipRating(id: Int, type: EventType): VolleyResult<Unit, ErrorType> {
        return when (type) {
            EventType.GAME -> gameRepository.skipRating(gameId = id)
            EventType.TOURNAMENT -> tournamentRepository.skipRating(tournamentId = id)
        }
    }
}
