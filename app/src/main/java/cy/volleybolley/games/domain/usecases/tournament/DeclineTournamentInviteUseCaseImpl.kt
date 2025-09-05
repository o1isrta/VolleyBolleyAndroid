package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.api.tournament.DeclineTournamentInviteUseCase

class DeclineTournamentInviteUseCaseImpl(
    private val repository: TournamentsRepository,
) : DeclineTournamentInviteUseCase {
    override suspend fun declineTournamentInvite(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        return repository.declineTournamentInvite(tournamentId)
    }
}
