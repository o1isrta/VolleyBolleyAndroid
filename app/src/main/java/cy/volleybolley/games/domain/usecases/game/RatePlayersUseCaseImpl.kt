package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GameRatingRepository
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.api.game.RatePlayersUseCase
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.event.EventType

class RatePlayersUseCaseImpl(
    private val gameRepository: GameRatingRepository,
    private val tournamentRepository: TournamentRatingRepository
) : RatePlayersUseCase {
    override fun ratePlayers(
        id: Int,
        type: EventType,
        players: List<RatePlayer>,
        onResult: (VolleyResult<Unit, ErrorType>) -> Unit
    ) {
        when (type) {
            EventType.GAME -> gameRepository.ratePlayers(
                gameId = id,
                players = players,
                onResult = onResult
            )

            EventType.TOURNAMENT -> tournamentRepository.ratePlayers(
                tournamentId = id,
                players = players,
                onResult = onResult
            )
        }
    }
}
