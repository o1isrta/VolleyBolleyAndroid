package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GameRatingRepository
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.api.game.GetPlayersToRateUseCase
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.EventType

class GetPlayersToRateUseCaseImpl(
    private val gameRepository: GameRatingRepository,
    private val tournamentRepository: TournamentRatingRepository
) : GetPlayersToRateUseCase {
    override suspend fun getPlayers(id: Int, type: EventType): VolleyResult<List<PlayerShort>, ErrorType> {
        return when (type) {
            EventType.GAME -> gameRepository.getPlayersToRate(gameId = id)
            EventType.TOURNAMENT -> tournamentRepository.getPlayersToRate(tournamentId = id)
        }
    }
}
