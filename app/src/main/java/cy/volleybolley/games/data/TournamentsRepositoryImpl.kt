package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toData
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.dto.mappers.toPlayersData
import cy.volleybolley.games.data.network.TournamentsRequest
import cy.volleybolley.games.data.network.TournamentsResponse
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.event.tournament.CreateTournament
import cy.volleybolley.games.domain.model.event.tournament.CreatedTournament
import cy.volleybolley.games.domain.model.event.tournament.JoinedTournament
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

class TournamentsRepositoryImpl(
    val networkClient: NetworkClient<TournamentsRequest, TournamentsResponse>
) : TournamentsRepository {

    override suspend fun createTournament(tournament: CreateTournament): VolleyResult<CreatedTournament, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.CreateTournament(tournament = tournament.toData()))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val tournament = (response.body as? TournamentsResponse.CreateTournament)?.toDomain()
        return tournament?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getTournamentDetails(tournamentId: Int): VolleyResult<TournamentDetails, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.GetTournamentDetails(tournamentId = tournamentId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val tournament = (response.body as? TournamentsResponse.GetTournamentDetails)?.toDomain()
        return tournament?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun invitePlayersToTournament(
        tournamentId: Int,
        players: List<PlayerShort>
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            TournamentsRequest.InvitePlayersToTournament(
                tournamentId = tournamentId,
                players = players.toPlayersData()
            )
        )

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun joinTournament(tournamentId: Int): VolleyResult<JoinedTournament, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.JoinTournament(tournamentId = tournamentId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val tournament = (response.body as? TournamentsResponse.JoinTournament)?.toDomain()
        return tournament?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun declineTournamentInvite(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        val response =
            networkClient.getResponse(TournamentsRequest.DeclineTournamentInvite(tournamentId = tournamentId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun getPlayersToRate(tournamentId: Int): VolleyResult<List<PlayerShort>, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.GetPlayersToRate(tournamentId = tournamentId))

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val preview = (response.body as? TournamentsResponse.GetPlayersToRate)?.players?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun ratePlayers(tournamentId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType> {
        val response =
            networkClient.getResponse(
                TournamentsRequest.RatePlayers(
                    tournamentId = tournamentId,
                    players = players.toData()
                )
            )

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun skipRating(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.SkipRating(tournamentId = tournamentId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
