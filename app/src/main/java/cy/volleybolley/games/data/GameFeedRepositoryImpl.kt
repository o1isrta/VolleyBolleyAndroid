package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GameFeedRepository
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.Preview

class GameFeedRepositoryImpl(
    private val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GameFeedRepository {
    override suspend fun getPreview(): VolleyResult<Preview, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetPreview())

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val preview = (response.body as? GamesResponse.GetPreview)?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getMyGames(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetMyGames())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val myGames = response.body as? GamesResponse.GetMyGames

        return myGames?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getInvites(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetInvites())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val invites = response.body as? GamesResponse.GetInvites

        return invites?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getArchive(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetArchive())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val archives = response.body as? GamesResponse.GetArchive

        return archives?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getUpcoming(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetUpcoming())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val upcoming = response.body as? GamesResponse.GetUpcoming

        return upcoming?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }
}
