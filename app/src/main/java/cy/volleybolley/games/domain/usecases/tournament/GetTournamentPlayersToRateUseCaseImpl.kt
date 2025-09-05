package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.api.tournament.GetTournamentPlayersToRateUseCase
import cy.volleybolley.games.domain.model.entity.PlayerShort

class GetTournamentPlayersToRateUseCaseImpl(
    private val repository: TournamentRatingRepository
) : GetTournamentPlayersToRateUseCase {
    override suspend fun getPlayersToRate(tournamentId: Int): VolleyResult<List<PlayerShort>, ErrorType> {
        return repository.getPlayersToRate(tournamentId = tournamentId)
    }
}
