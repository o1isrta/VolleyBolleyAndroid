package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.api.tournament.RateTournamentPlayersUseCase
import cy.volleybolley.games.domain.model.entity.RatePlayer

class RateTournamentPlayersUseCaseImpl(
    private val repository: TournamentRatingRepository
) : RateTournamentPlayersUseCase {
    override suspend fun ratePlayers(
        tournamentId: Int,
        players: List<RatePlayer>
    ): VolleyResult<Unit, ErrorType> {
        return repository.ratePlayers(tournamentId = tournamentId, players = players)
    }
}
