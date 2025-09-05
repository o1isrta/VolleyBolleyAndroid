package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentParticipationRepository
import cy.volleybolley.games.domain.api.tournament.JoinTournamentUseCase
import cy.volleybolley.games.domain.model.event.tournament.JoinedTournament

class JoinTournamentUseCaseImpl(
    private val repository: TournamentParticipationRepository
) : JoinTournamentUseCase {
    override suspend fun joinTournament(tournamentId: Int): VolleyResult<JoinedTournament, ErrorType> {
        return repository.joinTournament(tournamentId)
    }
}
