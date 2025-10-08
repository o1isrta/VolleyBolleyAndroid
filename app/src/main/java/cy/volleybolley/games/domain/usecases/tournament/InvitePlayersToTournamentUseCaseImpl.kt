package cy.volleybolley.games.domain.usecases.tournament

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.TournamentParticipationRepository
import cy.volleybolley.games.domain.api.tournament.InvitePlayersToTournamentUseCase
import cy.volleybolley.games.domain.model.entity.PlayerShort

class InvitePlayersToTournamentUseCaseImpl(
    private val repository: TournamentParticipationRepository
) : InvitePlayersToTournamentUseCase {
    override suspend fun invitePlayersToTournament(
        tournamentId: Int,
        players: List<PlayerShort>
    ): VolleyResult<Unit, ErrorType> {
        return repository.invitePlayersToTournament(tournamentId = tournamentId, players = players)
    }
}
